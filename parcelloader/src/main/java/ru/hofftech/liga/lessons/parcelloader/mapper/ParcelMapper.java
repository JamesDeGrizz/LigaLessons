package ru.hofftech.liga.lessons.parcelloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hofftech.liga.lessons.parcelloader.model.Parcel;
import ru.hofftech.liga.lessons.parcelloader.model.dto.ParcelDto;
import ru.hofftech.liga.lessons.parcelloader.model.entity.ParcelEntity;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ParcelMapper {
    static final String DELIMITER = ",";

    @Mapping(target = "content", source = "form")
    @Mapping(target = "id", ignore = true)
    public abstract ParcelEntity toParcelEntity(ParcelDto dto);

    @Mapping(target = "form", source = "content")
    public abstract ParcelDto toParcelDto(ParcelEntity entity);

    @Mapping(target = "content", source = "content", qualifiedByName = "stringToStringList")
    @Mapping(target = "placingPoints", ignore = true)
    public abstract Parcel toParcel(ParcelEntity entity);

    @Named("stringToStringList")
    public static List<String> stringToStringList(String content) {
        return Arrays.stream(content.split(DELIMITER)).toList();
    }
}
