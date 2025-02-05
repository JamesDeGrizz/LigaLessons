package ru.hofftech.liga.lessons.packageloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadParcelsUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;

@RestController
@Tag(name = "LogisticController", description = "Операции с посылками")
@RequestMapping(path = "/api/v1/logistic", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LogisticController {
    private final LoadParcelsUserCommandService loadParcelsUserCommandService;
    private final UnloadTrucksUserCommandService unloadTrucksUserCommandService;

    @PostMapping("/loading")
    public LoadParcelsUserCommandResponseDto load(@RequestBody LoadParcelsUserCommandDto dto) {
        return loadParcelsUserCommandService.execute(dto);
    }

    @PostMapping("/unloading")
    public UnloadTrucksUserCommandResponseDto unload(@RequestBody UnloadTrucksUserCommandDto dto) {
        return unloadTrucksUserCommandService.execute(dto);
    }
}
