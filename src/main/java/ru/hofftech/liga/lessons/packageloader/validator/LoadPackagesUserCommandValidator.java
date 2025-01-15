package ru.hofftech.liga.lessons.packageloader.validator;

import lombok.NoArgsConstructor;
import ru.hofftech.liga.lessons.packageloader.model.TruckSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class LoadPackagesUserCommandValidator {
    private static final String ARGUMENT_OUT_TYPE = "-out";
    private static final String ARGUMENT_OUT_TYPE_JSON = "json-file";
    private static final String ARGUMENT_OUT_FILENAME = "-out-filename";
    private static final String ARGUMENT_PACKAGES_TEXT = "-parcels-text";
    private static final String ARGUMENT_PACKAGES_FILE = "-parcels-file";
    private static final String ARGUMENT_ALGORITHM_TYPE = "-type";
    private static final String ARGUMENT_TRUCKS = "-trucks";

    public List<String> validate(Map<String, String> arguments) {
        List<String> errors = new ArrayList<String>();
        validateOutType(arguments, errors);
        validateReportFileName(arguments, errors);
        validatePackages(arguments, errors);
        validatePlacingAlgorithm(arguments, errors);
        validateTruckSizes(arguments, errors);
        return errors;
    }

    private void validateOutType(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_OUT_TYPE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_OUT_TYPE + "\"");
        }
    }

    private void validateReportFileName(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_OUT_TYPE) || !arguments.get(ARGUMENT_OUT_TYPE).equals(ARGUMENT_OUT_TYPE_JSON)) {
            return;
        }

        if (!arguments.containsKey(ARGUMENT_OUT_FILENAME)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_OUT_FILENAME + "\"");
        }
    }

    private void validatePackages(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_PACKAGES_TEXT) && !arguments.containsKey(ARGUMENT_PACKAGES_FILE)) {
            errors.add("Должен быть указан 1 из параметров: \"" + ARGUMENT_OUT_TYPE + "\" или \"" + ARGUMENT_OUT_FILENAME + "\"");
        }
    }

    private void validatePlacingAlgorithm(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_ALGORITHM_TYPE)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_ALGORITHM_TYPE + "\"");
            return;
        }

        var algorithmString = arguments.get(ARGUMENT_ALGORITHM_TYPE);
        try {
            var algorithm = Integer.parseInt(algorithmString);

            if (algorithm < 0 || algorithm > 2) {
                errors.add("Неправильное значение типа алгоритма: " + algorithmString);
            }
        }
        catch (Exception e) {
            errors.add("Введённое значение нельзя привести к числу: " + algorithmString);
        }
    }

    private void validateTruckSizes(Map<String, String> arguments, List<String> errors) {
        if (!arguments.containsKey(ARGUMENT_TRUCKS)) {
            errors.add("Не хватает аргумента \"" + ARGUMENT_TRUCKS + "\"");
            return;
        }

        var truckSizeList = arguments.get(ARGUMENT_TRUCKS).split("\\\\n");

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
