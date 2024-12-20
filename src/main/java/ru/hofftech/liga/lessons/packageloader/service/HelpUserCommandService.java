package ru.hofftech.liga.lessons.packageloader.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HelpUserCommandService implements UserCommandService{
    private final UserHelpService userHelpService;

    @Override
    public void execute() {
        userHelpService.printHelp();
    }
}
