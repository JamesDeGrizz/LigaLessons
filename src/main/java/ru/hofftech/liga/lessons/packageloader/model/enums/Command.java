package ru.hofftech.liga.lessons.packageloader.model.enums;

/**
 * Список возможных команд от пользователя
 */
public enum Command {
    /**
     * Разгрузка грузовиков
     */
    UNLOAD_TRUCKS,

    /**
     * Погрузка посылок
     */
    LOAD_PACKAGES,

    /**
     * Создание новой посылки
     */
    CREATE_PACKAGE,

    /**
     * Поиск посыл(ки/ок)
     */
    FIND_PACKAGE,

    /**
     * Редактирование посылки
     */
    EDIT_PACKAGE,

    /**
     * Кдаление посылки
     */
    DELETE_PACKAGE,

    SHOW_ORDERS,
}
