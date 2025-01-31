package ru.hofftech.liga.lessons.packageloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hofftech.liga.lessons.packageloader.model.Parcel;
import ru.hofftech.liga.lessons.packageloader.model.entity.ParcelEntity;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ParcelMapper {
    static final String DELIMITER = ",";

    @Mapping(target = "content", source = "content", qualifiedByName = "stringListToString")
    public abstract ParcelEntity toParcelEntity(Parcel dto);

    @Mapping(target = "content", source = "content", qualifiedByName = "stringToStringList")
    @Mapping(target = "placingPoints", ignore = true)
    public abstract Parcel toParcelDto(ParcelEntity entity);

    @Named("stringListToString")
    public static String stringListToString(List<String> content) {
        return String.join(DELIMITER, content);
    }

    @Named("stringToStringList")
    public static List<String> stringToStringList(String content) {
        return Arrays.stream(content.split(DELIMITER)).toList();
    }
}
