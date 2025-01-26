package ru.hofftech.liga.lessons.packageloader.service.command;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.packageloader.config.BeanNameConfig;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;
import ru.hofftech.liga.lessons.packageloader.validator.CreateParcelUserCommandValidator;

import java.util.Arrays;

/**
 * Сервис для создания посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд создания посылок.
 */
@AllArgsConstructor
@Service(value = BeanNameConfig.CREATE_PARCEL)
public class CreateParcelUserCommandService implements UserCommandService {
    private static final String DELIMITER = ",";

    private final ParcelRepository parcelRepository;
    private final CreateParcelUserCommandValidator commandValidator;
    private final ParcelMapper parcelMapper;

    @Override
    @Transactional
    public String execute(BaseUserCommandDto command) {
        if (command == null) {
            return "Посылка не может быть создана: \nПередан пустой список аргументов";
        }
        if (!(command instanceof CreateParcelUserCommandDto)) {
            return "Посылка не может быть создана: \nПередана команда неправильного типа";
        }

        var castedCommand = (CreateParcelUserCommandDto) command;

        var validationErrors = commandValidator.validate(castedCommand);
        if (!validationErrors.isEmpty()) {
            return "Посылка не может быть создана: \n" + String.join("\n", validationErrors);
        }

        var existingParcel = parcelRepository.findById(castedCommand.parcelId());
        if (existingParcel.isPresent()) {
            return "Посылка не может быть создана: \nпосылка с названием " + castedCommand.parcelId() + " уже существует";
        }

        var parcel = Parcel.builder()
                .name(castedCommand.parcelId())
                .content(Arrays.stream(castedCommand.form().split(DELIMITER)).toList())
                .symbol(castedCommand.symbol().charAt(0))
                .build();
        var parcelToSave = parcelMapper.toParcelEntity(parcel);
        var tmp = parcelRepository.save(parcelToSave);
        parcelRepository.flush();
        return "Посылка успешно создана";
    }
}
