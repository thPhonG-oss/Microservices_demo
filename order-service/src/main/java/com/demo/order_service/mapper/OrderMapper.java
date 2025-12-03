package com.demo.order_service.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
}
