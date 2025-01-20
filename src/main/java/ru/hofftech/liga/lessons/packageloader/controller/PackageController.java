package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.packageloader.model.dto.CreatePackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.DeletePackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.EditPackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.FindPackageUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;

@ShellComponent
@AllArgsConstructor
public class PackageController {
    private final UserCommandProcessorService userCommandProcessorService;

    @ShellMethod("Создание нового типа посылки")
    public String create(@ShellOption String name,
                         @ShellOption String symbol,
                         @ShellOption String form) {
        return userCommandProcessorService.processCommand(new CreatePackageUserCommandDto(name, form, symbol));
    }

    @ShellMethod("Поиск посылок")
    public String find(@ShellOption(defaultValue = "") String name) {
        return userCommandProcessorService.processCommand(new FindPackageUserCommandDto(name));
    }

    @ShellMethod("Обновление существующего типа посылки")
    public String edit(@ShellOption String id,
                         @ShellOption String name,
                         @ShellOption String symbol,
                         @ShellOption String form) {
        return userCommandProcessorService.processCommand(new EditPackageUserCommandDto(id, name, form, symbol));
    }

    @ShellMethod("Удаление посылки по названию")
    public String delete(@ShellOption String name) {
        return userCommandProcessorService.processCommand(new DeletePackageUserCommandDto(name));
    }
}
