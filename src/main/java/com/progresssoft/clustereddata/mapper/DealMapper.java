package com.progresssoft.clustereddata.mapper;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.dto.DealResponseDto;
import com.progresssoft.clustereddata.entity.Deal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealMapper {

    Deal toEntity(DealRequestDto dto);

    DealResponseDto toResponseEntity(Deal deal);
}