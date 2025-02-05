package ru.hofftech.liga.lessons.billing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.hofftech.liga.lessons.billing.model.dto.UserOrdersResponseDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderMapper {
    public abstract List<UserOrdersResponseDto> toFindUserOrdersUserResponseDtoList(List<OrderEntity> entity);
}
