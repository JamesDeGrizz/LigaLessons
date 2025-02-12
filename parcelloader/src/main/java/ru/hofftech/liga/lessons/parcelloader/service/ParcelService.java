package ru.hofftech.liga.lessons.parcelloader.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hofftech.liga.lessons.parcelloader.mapper.ParcelMapper;
import ru.hofftech.liga.lessons.parcelloader.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.parcelloader.repository.ParcelRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelService {
    private final ParcelRepository parcelRepository;
    private final ParcelMapper parcelMapper;

    public ParcelDto create(ParcelDto dto) {
        var existingParcel = parcelRepository.findByName(dto.name());
        if (existingParcel.isPresent()) {
            throw new IllegalArgumentException("Посылка с названием " + dto.name() + " уже существует");
        }

        var parcelToSave = parcelMapper.toParcelEntity(dto);
        var created = parcelRepository.save(parcelToSave);
        return parcelMapper.toParcelDto(created);
    }

    public void delete(String parcelName) {
        var existingParcel = parcelRepository.findByName(parcelName);
        if (existingParcel.isEmpty()) {
            throw new IllegalArgumentException("Посылка с названием " + parcelName + " не найдена");
        }

        parcelRepository.delete(existingParcel.get());
    }

    public void edit(String targetParcelName, ParcelDto dto) {
        var parcelToUpdateOptional = parcelRepository.findByName(targetParcelName);
        if (parcelToUpdateOptional.isEmpty()) {
            throw new IllegalArgumentException("Посылка с названием " + targetParcelName + " не существует");
        }

        var parcelToUpdate = parcelToUpdateOptional.get();
        parcelToUpdate.setName(dto.name());
        parcelToUpdate.setContent(dto.form());
        parcelToUpdate.setSymbol(dto.symbol().charAt(0));
        parcelRepository.save(parcelToUpdate);
    }

    public List<ParcelDto> findAll(int offset, int limit) {
        var pageNumber = offset / limit;
        var pageable = PageRequest.of(pageNumber, limit);
        var page = parcelRepository.findAll(pageable);

        return page.stream()
                .map(parcelMapper::toParcelDto)
                .toList();
    }

    public ParcelDto find(String parcelName) {
        var parcelOptional = parcelRepository.findByName(parcelName);
        if (parcelOptional.isEmpty()) {
            throw new IllegalArgumentException("Посылка с названием " + parcelName + " не существует");
        }

        return parcelMapper.toParcelDto(parcelOptional.get());
    }
}
