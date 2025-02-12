package ru.hofftech.liga.lessons.consoleclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.consoleclient.model.dto.LoadParcelsRequestDto;
import ru.hofftech.liga.lessons.consoleclient.model.dto.UnloadTrucksRequestDto;
import ru.hofftech.liga.lessons.consoleclient.service.LogisticService;

@ShellComponent
@RequiredArgsConstructor
public class LogisticController {
    private final LogisticService logisticService;

    @ShellMethod("Загрузка посылок в грузовики")
    public String load(@ShellOption String out,
                       @ShellOption String type,
                       @ShellOption String trucks,
                       @ShellOption(value = "user-id") String userId,
                       @ShellOption(value = "out-filename", defaultValue = "") String outFilename,
                       @ShellOption(value = "parcels-text", defaultValue = "") String parcelsText,
                       @ShellOption(value = "parcels-file", defaultValue = "") String parcelsFile) {
        return logisticService.load(new LoadParcelsRequestDto(out, type, trucks, userId, outFilename, parcelsText, parcelsFile));
    }

    @ShellMethod("Разгрузка грузовиков")
    public String unload(@ShellOption String infile,
                         @ShellOption String outfile,
                         @ShellOption(value = "user-id") String userId,
                         @ShellOption(value = "with-count", defaultValue = "false") boolean withCount) {
        return logisticService.unload(new UnloadTrucksRequestDto(infile, outfile, userId, withCount));
    }
}
