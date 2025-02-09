package ru.hofftech.liga.lessons.consoleclient.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.consoleclient.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.consoleclient.service.ParcelService;

@ShellComponent
@AllArgsConstructor
public class ParcelController {
    private final ParcelService parcelService;

    @ShellMethod("Создание нового типа посылки")
    public String create(@ShellOption String name,
                         @ShellOption String symbol,
                         @ShellOption String form) {
        return parcelService.createParcel(new ParcelDto(name, form, symbol));
    }

    @ShellMethod("Поиск посылок")
    public String find(@ShellOption(defaultValue = "") String name) {
        return parcelService.findParcel(name);
    }

    @ShellMethod("Обновление существующего типа посылки")
    public String edit(@ShellOption String targetParcelName,
                         @ShellOption String name,
                         @ShellOption String symbol,
                         @ShellOption String form) {
        return parcelService.updateParcel(targetParcelName, new ParcelDto(name, form, symbol));
    }

    @ShellMethod("Удаление посылки по названию")
    public String delete(@ShellOption String name) {
        return parcelService.deleteParcel(name);
    }
}
