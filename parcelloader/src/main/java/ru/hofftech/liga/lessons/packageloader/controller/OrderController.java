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
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindUserOrdersUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.command.DeleteParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.EditParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindParcelUserCommandService;
import ru.hofftech.liga.lessons.packageloader.service.command.FindUserOrdersCommandService;

@RestController
@Tag(name = "OrderController", description = "Операции, связанные с биллингом")
@RequestMapping(path = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
    private final FindUserOrdersCommandService findUserOrdersCommandService;

    @GetMapping("/{userid}")
    public String findUserOrders(@PathVariable String userid) {
        return findUserOrdersCommandService.execute(new FindUserOrdersUserCommandDto(userid));
    }
}
