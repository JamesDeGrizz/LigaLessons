package ru.hofftech.liga.lessons.packageloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.command.EditParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.LoadParcelsUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.UnloadTrucksUserCommandService;

@RestController
@Tag(name = "LogisticController", description = "Операции с посылками")
@RequestMapping(path = "/api/v1/logistic", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LogisticController {
    private final LoadParcelsUserCommandService loadParcelsUserCommandService;
    private final UnloadTrucksUserCommandService unloadTrucksUserCommandService;

    @PostMapping("/load")
    public String load(@RequestBody LoadParcelsUserCommandDto dto) {
        return loadParcelsUserCommandService.execute(dto);
    }

    @PostMapping("/unload")
    public String unload(@RequestBody UnloadTrucksUserCommandDto dto) {
        return unloadTrucksUserCommandService.execute(dto);
    }
}
