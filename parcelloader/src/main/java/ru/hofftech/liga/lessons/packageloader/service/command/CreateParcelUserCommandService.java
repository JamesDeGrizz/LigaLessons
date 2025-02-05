package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;

import java.util.Arrays;

/**
 * Сервис для создания посылок на основе команд пользователя.
 */
@AllArgsConstructor
public class CreateParcelUserCommandService {
    private static final String DELIMITER = ",";
    private static final String BASE_ERROR_MESSAGE_TEXT = "Посылка не может быть создана: ";

    private final ParcelRepository parcelRepository;
    private final CreateParcelUserCommandValidator commandValidator;
    private final ParcelMapper parcelMapper;

    public CreateParcelUserCommandResponseDto execute(CreateParcelUserCommandDto command) {
        if (command == null) {
            throw new IllegalArgumentException(BASE_ERROR_MESSAGE_TEXT + "\nПередан пустой список аргументов");
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException(BASE_ERROR_MESSAGE_TEXT + "\n" + String.join("\n", validationErrors));
        }

        var existingParcel = parcelRepository.findByName(command.parcelId());
        if (existingParcel.isPresent()) {
            throw new IllegalArgumentException(BASE_ERROR_MESSAGE_TEXT + "\nпосылка с названием " + command.parcelId() + " уже существует");
        }

        var parcel = Parcel.builder()
                .name(command.parcelId())
                .content(Arrays.stream(command.form().split(DELIMITER)).toList())
                .symbol(command.symbol().charAt(0))
                .build();
        var parcelToSave = parcelMapper.toParcelEntity(parcel);
        var created = parcelRepository.save(parcelToSave);
        return CreateParcelUserCommandResponseDto.builder()
                .parcelId(created.getName())
                .form(created.getContent())
                .symbol(String.valueOf(created.getSymbol()))
                .build();
    }
}
