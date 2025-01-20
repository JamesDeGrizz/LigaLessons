package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;
import ru.hofftech.liga.lessons.packageloader.model.dto.LoadPackagesUserCommandDto;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class LoadPackagesUserCommandValidator {
    private static final String ARGUMENT_OUT_TYPE_JSON = "json-file";
    private static final String ARGUMENT_OUT_TYPE_TEXT = "text";

    public List<String> validate(LoadPackagesUserCommandDto command) {
        List<String> errors = new ArrayList<String>();
        validateOut(command, errors);
        validateReportFileName(command, errors);
        validatePackages(command, errors);
        validatePlacingAlgorithm(command, errors);
        validateTruckSizes(command, errors);
        validateUserId(command, errors);
        return errors;
    }

    private void validateUserId(LoadPackagesUserCommandDto command, List<String> errors) {
        if (command.userId() == null || command.userId().isEmpty()) {
            errors.add("Не хватает аргумента \"user-id\"");
        }
    }

    private void validateOut(LoadPackagesUserCommandDto command, List<String> errors) {
        if (command.out() == null || command.out().isEmpty()) {
            errors.add("Не хватает аргумента \"out\"");
        }
        else if (!command.out().equals(ARGUMENT_OUT_TYPE_JSON) && !command.out().equals(ARGUMENT_OUT_TYPE_TEXT)) {
            errors.add("Неправильное значение типа аргумента \"out\". Доступно \"" + ARGUMENT_OUT_TYPE_JSON + "\" и \"" + ARGUMENT_OUT_TYPE_TEXT + "\"");
        }
    }

    private void validateReportFileName(LoadPackagesUserCommandDto command, List<String> errors) {
        if (command.out() != null || !command.out().equals(ARGUMENT_OUT_TYPE_JSON)) {
            return;
        }

        if (command.outFilename() == null || command.outFilename().isEmpty()) {
            errors.add("Не хватает аргумента \"out-filename\"");
        }
    }

    private void validatePackages(LoadPackagesUserCommandDto command, List<String> errors) {
        if ((command.parcelsText() == null || command.parcelsText().isEmpty())
                && (command.parcelsFile() == null || command.parcelsFile().isEmpty())) {
            errors.add("Должен быть указан 1 из параметров: \"parcels-text\" или \"parcels-file\"");
        }
    }

    private void validatePlacingAlgorithm(LoadPackagesUserCommandDto command, List<String> errors) {
        if (command.type() == null || command.type().isEmpty()) {
            errors.add("Не хватает аргумента \"type\"");
            return;
        }

        try {
            var algorithm = Integer.parseInt(command.type());

            if (algorithm < 0 || algorithm > 2) {
                errors.add("Неправильное значение типа алгоритма: " + command.type());
            }
        }
        catch (Exception e) {
            errors.add("Введённое значение нельзя привести к числу: " + command.type());
        }
    }

    private void validateTruckSizes(LoadPackagesUserCommandDto command, List<String> errors) {
        if (command.trucks() == null || command.trucks().isEmpty()) {
            errors.add("Не хватает аргумента \"trucks\"");
            return;
        }

        var truckSizeList = command.trucks().split(",");

        for (var truckSize : truckSizeList) {
            var widthAndHeight = truckSize.split("x");
            if (widthAndHeight.length != 2) {
                errors.add("Неправильный формат размера кузова грузовика: " + truckSize);
                continue;
            }

            try {
                var width = Integer.parseInt(widthAndHeight[0]);
                var height = Integer.parseInt(widthAndHeight[1]);

                if (width < 0 || width > TruckSize.TRUCK_MAX_WIDTH) {
                    errors.add("Неправильное значение ширины грузовика: " + width);
                }

                if (height < 0 || height > TruckSize.TRUCK_MAX_HEIGHT) {
                    errors.add("Неправильное значение высоты грузовика: " + height);
                }
            }
            catch (Exception e) {
                errors.add("Неправильный формат размера кузова грузовика: " + truckSize);
            }
        }
    }
}
