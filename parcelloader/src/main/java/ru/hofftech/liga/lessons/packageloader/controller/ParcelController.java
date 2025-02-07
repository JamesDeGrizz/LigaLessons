package ru.hofftech.liga.lessons.packageloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.packageloader.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.packageloader.service.ParcelService;

import java.util.List;

@RestController
@Validated
@Tag(name = "ParcelController", description = "CRUD операции посылок")
@RequestMapping(path = "/api/v1/parcels", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ParcelController {
    private final ParcelService parcelService;

    @GetMapping
    public List<ParcelDto> findAll(
            @RequestParam @Min(0) int offset,
            @RequestParam @Min(1) @Max(1000) int limit
    ) {
        return parcelService.findAll(offset, limit);
    }

    @GetMapping("/{parcelName}")
    public ParcelDto find(@PathVariable @NotBlank String parcelName) {
        return parcelService.find(parcelName);
    }

    @PostMapping()
    public ParcelDto create(@RequestBody @Validated ParcelDto dto) {
        return parcelService.create(dto);
    }

    @PutMapping("/{targetParcelName}")
    public void update(@PathVariable @NotBlank String targetParcelName,
                       @RequestBody @Validated ParcelDto dto) {
        parcelService.edit(targetParcelName, dto);
    }

    @DeleteMapping("/{parcelName}")
    public void delete(@PathVariable @NotBlank String parcelName) {
        parcelService.delete(parcelName);
    }
}
