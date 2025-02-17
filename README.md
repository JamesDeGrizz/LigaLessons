# Утилита для работы с посылками 

## Описание
Этот проект представляет собой систему для управления посылками и грузовиками. Он включает в себя различные команды для создания, поиска, редактирования и удаления посылок, а также для загрузки и разгрузки грузовиков.

## Запуск
Из коробки, без прелюдий и дополнительной подготовки

## Доступные интерфейсы
- консоль
- телеграм (@LigaLessonsKai)

## Доступные по умолчанию посылки
- "Посылка тип 1", форма "1", символ "1"
- "Посылка тип 2", форма "22", символ "2"
- "Посылка тип 3", форма "333", символ "3"
- "Посылка тип 4", форма "4444", символ "4"
- "Посылка тип 5", форма "55555", символ "5"
- "Посылка тип 6", форма "666\n666", символ "6"
- "Посылка тип 7", форма "777\n7777", символ "7"
- "Посылка тип 8", форма "8888\n8888", символ "8"
- "Посылка тип 9", форма "999\n999\n999", символ "9"

## Доступные команды:
- [create](#Create) - создание нового типа посылки;
- [find](#Find) - поиск существующего типа посылки;
- [edit](#Edit) - редактирование существующего типа посылки;
- [delete](#Delete) - удаление существующего типа посылки;
- [load](#Load) - загрузка посылок из файла или команды;
- [unload](#Unload) - выгрузка посылок из грузовиков;
- [help](#Help) - справка;
- [exit](#Exit) - завершение работы приложения;

## Create
Создание нового типа посылки.  
#### Список аргументов:
- -name - имя/guid посылки. Обязателен. Значение указывается в двойных кавычках. Пример - "Квадратное колесо"
- -form - форма посылки. Обязателен. Значение указывается в двойных кавычках. Если посылка больше 1 в высоту, используйте разделитель "\n". Пример - "xxx\nx x\nxxx"
- -symbol - символ, которым посылка будет отображаться. Обязателен. Значение указывается в двойных кавычках. Пример - "0"
#### Пример:
- create -name "Один ряд" -form "qqqqqq" -symbol "t"
- create -name "Квадратное колесо" -form "xxx\nx x\nxxx" -symbol "o"

## Find
Поиск существующего типа посылки. Если не указывать название посылки, выведет все существующие.  
#### Список аргументов:
- "package name" - название посылки. Необязателен
#### Пример:
- find
- find "Посылка Тип 4"

## Edit
Редактирование существующего типа посылки.  
#### Список аргументов:
- -id - имя/guid существующей посылки. Обязателен. Значение указывается в двойных кавычках. Пример - "Квадратное колесо"
- -name - имя/guid посылки. Обязателен. Значение указывается в двойных кавычках. Пример - "Квадратное колесо"
- -form - форма посылки. Обязателен. Значение указывается в двойных кавычках. Если посылка больше 1 в высоту, используйте разделитель "\n". Пример - "xxx\nx x\nxxx"
- -symbol - символ, которым посылка будет отображаться. Обязателен. Значение указывается в двойных кавычках. Пример - "0"
#### Пример:
- edit -id "Квадратное колесо" -name "КУБ" -form "xxx\nxxx\nxxx" -symbol "%"

## Delete
Удаление существующего типа посылки.  
#### Список аргументов:
- "package name" - название посылки. Обязателен
#### Пример:
- delete "Посылка Тип 4"

## Load
Загрузка посылок из файла или команды.  
#### Список аргументов:
- -parcels-text - загрузка из текста команды. Условно обязателен. Значение указывается в двойных кавычках через разделитель "\n". Пример - "Квадратное колесо"
- -parcels-file - загрузка из файла. Условно обязателен. Значение указывается в двойных кавычках, формат файла только .txt. Пример - "Квадратное колесо"
- -trucks - размеры грузовиков. Обязателен. Значение указывается в двойных кавычках в формате "width x height" через разделитель "\n". Пример - "3x3\n3x3\n6x2"
- -type - тип алгоритма погрузки. Обязателен. Значение указывается в двойных кавычках. Пример - "0"
- -out - формат отчета. Обязателен. Возможные значения - "text", "json-file"
- -out-filename - название файла отчета. Обязателен, если указан -out "json-file". Значение указывается в двойных кавычках. Пример - "trucks.json"
#### Возможные значения параметра -type:
- 0 - один грузовик = одна посылка
- 1 - один грузовик = максимум посылок
- 2 - равномерная погрузка по машинам
#### Пример:
- load -parcels-text "Посылка Тип 1\nПосылка Тип 4\nКУБ" -trucks "3x3\n3x3\n6x2" -type "0" -out "text"
- load -parcels-file "parcels.txt" -trucks "3x3\n3x3\n6x2" -type "1" -out "json-file" -out-filename "trucks.json"

## Unload
Выгрузка посылок из грузовиков.
#### Список аргументов:
- -infile - файл с описанием размеров и содержимого грузовиков. Обязателен. Значение указывается в двойных кавычках, формат файла .json. Пример - "trucks.json"
- -outfile - файл с отчетом о выгруженных посылках. Обязателен. Значение указывается в двойных кавычках. Пример - "parcels.txt"
- --withcount - флаг, при выставлении которого в отчете будет посчитано количество посылок каждого типа. Необязателен. Значения нет
#### Пример:
- unload -infile "trucks.json" -outfile "parcels-with-count.txt" --withcount
- unload -infile "trucks.json" -outfile "parcels.txt"

## Help
Выводит справку.  
Команда без аргументов.  
#### Пример:
- help

## Exit
Завершает работу программы.  
Команда без аргументов.  
#### Пример:
- exit