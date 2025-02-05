package ru.hofftech.liga.lessons.packageloader.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;
import ru.hofftech.liga.lessons.packageloader.repository.ParcelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для поиска посылок на основе команд пользователя.
 */
@RequiredArgsConstructor
public class FindParcelUserCommandService {
    // todo: в настройки
    private static final int PAGE_SIZE = 1000;

    private final ParcelRepository parcelRepository;

    public FindParcelUserCommandResponseDto execute(FindParcelUserCommandDto command) {
        if (command == null || command.parcelId() == null || command.parcelId().isEmpty()) {
            var pageNumber = 0;
            Page<ParcelEntity> page;
            List<ParcelEntity> parcels = new ArrayList<>();

            do {
                // todo: вытащить пагинацию в ендпоинт
                var pageable = PageRequest.of(pageNumber++, PAGE_SIZE);
                page = parcelRepository.findAll(pageable);
                parcels.addAll(page.stream().toList());
            } while (page.hasNext());

            return FindParcelUserCommandResponseDto.builder()
                    .parcels(parcels.stream()
                            .map(parcelEntity ->
                                    FindParcelUserCommandResponseDto.FindParcelUserCommandResponseListItemDto.builder()
                                            .parcelId(parcelEntity.getName())
                                            .form(parcelEntity.getContent())
                                            .symbol(String.valueOf(parcelEntity.getSymbol()))
                                            .build())
                            .collect(Collectors.toList()))
                    .build();
        }

        var parcelOptional = parcelRepository.findByName(command.parcelId());
        if (parcelOptional.isEmpty()) {
            throw new IllegalArgumentException("Посылка с названием " + command.parcelId() + " не существует");
        }

        var parcel = parcelOptional.get();
        return FindParcelUserCommandResponseDto.builder()
                .parcels(List.of(
                        FindParcelUserCommandResponseDto.FindParcelUserCommandResponseListItemDto.builder()
                                .parcelId(parcel.getName())
                                .form(parcel.getContent())
                                .symbol(String.valueOf(parcel.getSymbol()))
                                .build()
                ))
                .build();
    }
}
