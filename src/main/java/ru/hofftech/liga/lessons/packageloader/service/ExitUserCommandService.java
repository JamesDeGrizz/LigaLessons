package ru.hofftech.liga.lessons.packageloader.service;

public class ExitUserCommandService implements UserCommandService{
    @Override
    public void execute() {
        System.exit(0);
    }
}
