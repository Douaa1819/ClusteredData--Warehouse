package com.progresssoft.clustereddata.services;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.dto.DealResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface DealService {
    DealResponseDto saveDeal(DealRequestDto dealRequestDto);
}
