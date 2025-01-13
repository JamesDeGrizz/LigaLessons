package ru.hofftech.liga.lessons.packageloader.service.interfaces;

import java.util.Map;

public interface UserCommandService {
    String execute(Map<String, String> arguments);
}
