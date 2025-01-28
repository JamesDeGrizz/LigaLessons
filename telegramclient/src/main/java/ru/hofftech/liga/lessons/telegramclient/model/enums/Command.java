package ru.hofftech.liga.lessons.telegramclient.model.enums;

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
    LOAD_PARCELS,

    /**
     * Создание новой посылки
     */
    CREATE_PARCEL,

    /**
     * Поиск посыл(ки/ок)
     */
    FIND_PARCEL,

    /**
     * Редактирование посылки
     */
    EDIT_PARCEL,

    /**
     * Удаление посылки
     */
    DELETE_PARCEL,

    SHOW_ORDERS,
}
