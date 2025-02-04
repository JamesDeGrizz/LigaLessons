package ru.hofftech.liga.lessons.billing.mapper;

import org.mapstruct.Mapper;
import ru.hofftech.liga.lessons.billing.model.dto.FindUserOrdersUserResponseDto;
import ru.hofftech.liga.lessons.billing.model.entity.OrderEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public abstract List<FindUserOrdersUserResponseDto> toFindUserOrdersUserResponseDtoList(List<OrderEntity> entity);
}
