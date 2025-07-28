package com.progresssoft.clustereddata.services.impl;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.dto.DealResponseDto;
import com.progresssoft.clustereddata.entity.Deal;
import com.progresssoft.clustereddata.exception.RequestAlreadyExistException;
import com.progresssoft.clustereddata.mapper.DealMapper;
import com.progresssoft.clustereddata.repository.DealRepository;
import com.progresssoft.clustereddata.services.DealService;
import com.progresssoft.clustereddata.validator.CurrencyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j

public class DealServiceImpl implements DealService {
    private final DealMapper dealMapper;
    private final DealRepository dealRepository;
    private final CurrencyValidator currencyValidator;


    @Override
    public DealResponseDto saveDeal(DealRequestDto dto) {
        log.info("Received deal for saving: {}", dto);
        currencyValidator.validateCurrencyRules(dto) ;
        if (dealRepository.existsById(dto.id())) {
            throw new RequestAlreadyExistException("Deal already exists with ID: " + dto.id());
        }


        Deal deal = dealMapper.toEntity(dto);
        deal.setDealTimestamp(LocalDateTime.now());

        Deal saved = dealRepository.save(deal);

        return dealMapper.toResponseEntity(saved);
    }
}


