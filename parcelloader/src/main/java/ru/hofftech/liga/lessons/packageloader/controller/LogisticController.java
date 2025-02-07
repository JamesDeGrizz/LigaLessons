package ru.hofftech.liga.lessons.packageloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsResponseDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksResponseDto;
import ru.hofftech.liga.lessons.packageloader.service.LoadParcelsService;
import ru.hofftech.liga.lessons.packageloader.service.UnloadTrucksService;

@RestController
@Tag(name = "LogisticController", description = "Операции с посылками")
@RequestMapping(path = "/api/v1/logistics", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LogisticController {
    private final LoadParcelsService loadParcelsService;
    private final UnloadTrucksService unloadTrucksService;

    @PostMapping("/loading")
    public LoadParcelsResponseDto load(@RequestBody LoadParcelsUserCommandDto dto) {
        return loadParcelsService.execute(dto);
    }

    @PostMapping("/unloading")
    public UnloadTrucksResponseDto unload(@RequestBody UnloadTrucksUserCommandDto dto) {
        return unloadTrucksService.execute(dto);
    }
}
