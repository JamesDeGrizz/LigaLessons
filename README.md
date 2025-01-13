# Утилита для работы с посылками 

## Запуск
Из коробки, без прелюдий и дополнительной подготовки

## Интерфейсы
- консоль
- телеграм (@LigaLessonsKai)

## Доступные команды:
- help - справка;
- create - создание нового типа посылки;
- find - поиск существующего типа посылки;
- edit - редактирование существующего типа посылки;
- delete - удаление существующего типа посылки;
- load - загрузка файла с посылками;
- unload - загрузка файла с грузовиками;
- exit - завершение работы;

## Примеры команд:
- create -name "Квадратное колесо" -form "xxx\nx x\nxxx" -symbol "o",
- find "Посылка Тип 4",
- edit -id "Квадратное колесо" -name "КУБ" -form "xxx\nxxx\nxxx" -symbol "%",
- delete "Посылка Тип 4",
- load -parcels-text "Посылка Тип 1\nПосылка Тип 4\nКУБ" -trucks "3x3\n3x3\n6x2" -type "0" -out "text",
- load -parcels-file "parcels.txt" -trucks "3x3\n3x3\n6x2" -type "1" -out "json-file" -out-filename "trucks.json",
- unload -infile "trucks.json" -outfile "parcels-with-count.txt" --withcount,
- unload -infile "trucks.json" -outfile "parcels.csv"

## Выбор алгоритма погрузки для команды load, параметр -type:
- 0 - один грузовик = одна посылка
- 1 - один грузовик = максимум посылок
- 2 - равномерная погрузка по машинам

## Warning
Посылки хранятся только в файлах формата .txt, а грузовики - в .json.