package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;

import java.util.Arrays;

/**
 * Сервис для создания посылок на основе команд пользователя.
 */
@AllArgsConstructor
public class CreateParcelUserCommandService {
    private static final String DELIMITER = ",";

    private final ParcelRepository parcelRepository;
    private final CreateParcelUserCommandValidator commandValidator;
    private final ParcelMapper parcelMapper;

    public String execute(CreateParcelUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть создана: \nПередан пустой список аргументов";
        }

        var validationErrors = commandValidator.validate(command);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть создана: \n" + String.join("\n", validationErrors);
        }

        var existingParcel = parcelRepository.findByName(command.parcelId());
        if (existingParcel.isPresent()) {
            return "Посылка не может быть создана: \nпосылка с названием " + command.parcelId() + " уже существует";
        }

        var parcel = Parcel.builder()
                .name(command.parcelId())
                .content(Arrays.stream(command.form().split(DELIMITER)).toList())
                .symbol(command.symbol().charAt(0))
                .build();
        var parcelToSave = parcelMapper.toParcelEntity(parcel);
        var created = parcelRepository.save(parcelToSave);
        return "Посылка успешно создана:\n" + parcelMapper.toParcelDto(created);
    }
}
