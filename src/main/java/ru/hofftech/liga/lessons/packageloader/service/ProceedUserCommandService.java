package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProceedUserCommandService implements UserCommandService {
    private final PackageService packageService;
    private final UserConsoleService userCommandService;

    @Override
    public void execute() {
        packageService.placePackagesFromFileIntoTrucks(userCommandService.getFileName(), userCommandService.getAlgorithm());
    }
}
