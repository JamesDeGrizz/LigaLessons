package ru.hofftech.liga.lessons.packageloader.model.enums;

/**
 * Список возможных команд от пользователя
 */
public enum Command {
    /**
     * Разгрузка грузовиков
     */
    UnloadTrucks,

    /**
     * Погрузка посылок
     */
    LoadPackages,

    /**
     * Завершение работы приложения
     */
    Exit,

    /**
     * Неправильный ввод
     */
    Retry,

    /**
     * Вывод справки
     */
    Help,

    /**
     * Создание новой посылки
     */
    CreatePackage,

    /**
     * Поиск посыл(ки/ок)
     */
    FindPackage,

    /**
     * Редактирование посылки
     */
    EditPackage,

    /**
     * Кдаление посылки
     */
    DeletePackage,
}
