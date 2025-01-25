package ru.hofftech.liga.lessons.packageloader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface ParcelSymbolValuesMapper {

    @Named("symbolExtractor")
    public static char symbolExtractor(String content) {
        return content.charAt(0);
    }
}
