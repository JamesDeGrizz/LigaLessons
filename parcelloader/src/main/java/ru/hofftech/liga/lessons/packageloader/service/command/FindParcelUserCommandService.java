package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.hofftech.liga.lessons.packageloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;

/**
 * Сервис для поиска посылок на основе команд пользователя.
 */
@RequiredArgsConstructor
public class FindParcelUserCommandService {
    private static final String ERROR_MESSAGE_TEXT = "Посылка не может быть отредактирована: ";
    private static final int PAGE_SIZE = 1000;

    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public String execute(FindParcelUserCommandDto command) {
        if (command == null || command.parcelId() == null || command.parcelId().isEmpty()) {
            var pageNumber = 0;
            var stringBuilder = new StringBuilder();
            Page<ParcelEntity> page;

            do {
                 var pageable = PageRequest.of(pageNumber++, PAGE_SIZE);
                page = parcelRepository.findAll(pageable);
                for (var parcel : page) {
                    stringBuilder.append(parcelMapper.toParcelDto(parcel).toString());
                }
            } while (page.hasNext());

            return stringBuilder.toString();
        }

        var parcel = parcelRepository.findByName(command.parcelId());
        if (parcel.isEmpty()) {
            return ERROR_MESSAGE_TEXT + "\nпосылка с названием " + command.parcelId() + " не существует";
        }

        return parcelMapper.toParcelDto(parcel.get()).toString();
    }
}
