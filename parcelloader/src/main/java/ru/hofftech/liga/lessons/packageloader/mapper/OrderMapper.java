package ru.hofftech.liga.lessons.packageloader.mapper;

import org.mapstruct.Mapper;
import ru.hofftech.liga.lessons.packageloader.model.Order;
import ru.hofftech.liga.lessons.packageloader.model.entity.OrderEntity;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public abstract OrderEntity toOrderEntity(Order dto);

    public abstract Order toOrderDto(OrderEntity entity);
}
