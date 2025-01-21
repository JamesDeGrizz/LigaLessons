package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.dto.BaseUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;
import ru.hofftech.liga.lessons.packageloader.service.interfaces.UserCommandService;

/**
 * Сервис для поиска посылок на основе команд пользователя.
 * Этот класс реализует интерфейс {@link UserCommandService} и предоставляет методы для обработки команд поиска посылок.
 */
@AllArgsConstructor
public class FindParcelUserCommandService implements UserCommandService {
    private final ParcelRepository parcelRepository;

    @Override
    public String execute(BaseUserCommandDto command) {
        if (!(command instanceof FindParcelUserCommandDto)) {
            return "Посылка не может быть выведена: \nПередана команда неправильного типа";
        }

        var castedCommand = (FindParcelUserCommandDto) command;

        if (castedCommand.parcelId() == null || castedCommand.parcelId().isEmpty()) {
            var parcels = parcelRepository.findAll();
            var stringBuilder = new StringBuilder();
            for (var parcel : parcels) {
                stringBuilder.append(parcel.toString());
            }
            return stringBuilder.toString();
        }

        var parcel = parcelRepository.find(castedCommand.parcelId());
        if (parcel == null || !parcel.isPresent()) {
            return "Посылка не может быть выведена: \nпосылка с названием " + castedCommand.parcelId() + " не существует";
        }

        return parcel.get().toString();
    }
}
