package ru.hofftech.liga.lessons.packageloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper
public interface ParcelContentValuesMapper {
    static final String DELIMITER = ",";

    @Named("stringListToString")
    public static String stringListToString(List<String> content) {
        return String.join(DELIMITER, content);
    }

    @Named("stringToStringList")
    public static List<String> stringToStringList(String content) {
        return Arrays.stream(content.split(DELIMITER)).toList();
    }
}
