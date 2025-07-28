package com.progresssoft.clustereddata.mapper;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.dto.DealResponseDto;
import com.progresssoft.clustereddata.entity.Deal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DealMapper {

    @Mapping(source = "id", target = "id")
    Deal toEntity(DealRequestDto dto);

    DealResponseDto toResponseEntity(Deal deal);
}