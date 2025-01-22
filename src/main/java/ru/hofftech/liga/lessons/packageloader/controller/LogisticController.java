package ru.hofftech.liga.lessons.packageloader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadParcelsUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.model.dto.UnloadTrucksUserCommandDto;
import ru.hofftech.liga.lessons.packageloader.service.UserCommandProcessorService;

@ShellComponent
@RequiredArgsConstructor
public class LogisticController {
    private final UserCommandProcessorService userCommandProcessorService;

    @ShellMethod("Загрузка посылок в грузовики")
    public String load(@ShellOption String out,
                       @ShellOption String type,
                       @ShellOption String trucks,
                       @ShellOption(value = "user-id") String userId,
                       @ShellOption(value = "out-filename", defaultValue = "") String outFilename,
                       @ShellOption(value = "parcels-text", defaultValue = "") String parcelsText,
                       @ShellOption(value = "parcels-file", defaultValue = "") String parcelsFile) {
        return userCommandProcessorService.processCommand(new LoadParcelsUserCommandDto(out, type, trucks, userId, outFilename, parcelsText, parcelsFile));
    }

    @ShellMethod("Разгрузка грузовиков")
    public String unload(@ShellOption String infile,
                         @ShellOption String outfile,
                         @ShellOption(value = "user-id") String userId,
                         @ShellOption(value = "with-count", defaultValue = "false") boolean withCount) {
        return userCommandProcessorService.processCommand(new UnloadTrucksUserCommandDto(infile, outfile, userId, withCount));
    }
}
