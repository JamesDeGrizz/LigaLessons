package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;

/**
 * Сервис для поиска посылок на основе команд пользователя.
 */
@AllArgsConstructor
public class FindParcelUserCommandService {
    private static final String ERROR_MESSAGE_TEXT = "Посылка не может быть отредактирована: ";

    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public String execute(FindParcelUserCommandDto command) {
        if (command == null || command.parcelId() == null || command.parcelId().isEmpty()) {

            var page = PageRequest.of(0, Integer.MAX_VALUE);
            var parcels = parcelRepository.findAll(page);
            var stringBuilder = new StringBuilder();
            for (var parcel : parcels) {
                stringBuilder.append(parcelMapper.toParcelDto(parcel).toString());
            }
            return stringBuilder.toString();
        }

        var parcel = parcelRepository.findByName(command.parcelId());
        if (!parcel.isPresent()) {
            return ERROR_MESSAGE_TEXT + "\nпосылка с названием " + command.parcelId() + " не существует";
        }

        return parcelMapper.toParcelDto(parcel.get()).toString();
    }
}
