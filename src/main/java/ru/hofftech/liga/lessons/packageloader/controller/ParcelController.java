package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreateParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeleteParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindParcelUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;

@ShellComponent
@AllArgsConstructor
public class ParcelController {
    private final UserCommandProcessorService userCommandProcessorService;

    @ShellMethod("Создание нового типа посылки")
    public String create(@ShellOption String name,
                         @ShellOption String symbol,
                         @ShellOption String form) {
        return userCommandProcessorService.processCommand(new CreateParcelUserCommandDto(name, form, symbol));
    }

    @ShellMethod("Поиск посылок")
    public String find(@ShellOption(defaultValue = "") String name) {
        return userCommandProcessorService.processCommand(new FindParcelUserCommandDto(name));
    }

    @ShellMethod("Обновление существующего типа посылки")
    public String edit(@ShellOption String id,
                         @ShellOption String name,
                         @ShellOption String symbol,
                         @ShellOption String form) {
        return userCommandProcessorService.processCommand(new EditParcelUserCommandDto(id, name, form, symbol));
    }

    @ShellMethod("Удаление посылки по названию")
    public String delete(@ShellOption String name) {
        return userCommandProcessorService.processCommand(new DeleteParcelUserCommandDto(name));
    }
}
