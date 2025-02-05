package ru.hofftech.liga.lessons.packageloader.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandResponseDto;
import ru.hofftech.liga.lessons.packageloader.service.command.CreateParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.DeleteParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindParcelUserCommandService;

@RestController
@Tag(name = "ParcelController", description = "CRUD операции посылок")
@RequestMapping(path = "/api/v1/parcels", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ParcelController {
    private final CreateParcelUserCommandService createParcelUserCommandService;
    private final DeleteParcelUserCommandService deleteParcelUserCommandService;
    private final FindParcelUserCommandService findParcelUserCommandService;
    private final EditParcelUserCommandService editParcelUserCommandService;

    @GetMapping
    public FindParcelUserCommandResponseDto findAll() {
        return findParcelUserCommandService.execute(new FindParcelUserCommandDto(null));
    }

    @GetMapping("/{parcelId}")
    public FindParcelUserCommandResponseDto find(@PathVariable String parcelId) {
        return findParcelUserCommandService.execute(new FindParcelUserCommandDto(parcelId));
    }

    @PostMapping()
    public CreateParcelUserCommandResponseDto create(@RequestBody CreateParcelUserCommandDto dto) {
        return createParcelUserCommandService.execute(dto);
    }

    @PutMapping("/{parcelId}")
    public EditParcelUserCommandResponseDto update(@PathVariable String parcelId,
                                                   @RequestBody CreateParcelUserCommandDto dto) {
        return editParcelUserCommandService.execute(new EditParcelUserCommandDto(parcelId, dto.parcelId(), dto.form(), dto.symbol()));
    }

    @DeleteMapping("/{parcelId}")
    public void delete(@PathVariable String parcelId) {
        deleteParcelUserCommandService.execute(new DeleteParcelUserCommandDto(parcelId));
    }
}
