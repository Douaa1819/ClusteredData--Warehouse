package com.progresssoft.clustereddata.controller;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.dto.DealResponseDto;
import com.progresssoft.clustereddata.services.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping
    public ResponseEntity<DealResponseDto> createDeal (@Valid @RequestBody DealRequestDto dto ) {
        DealResponseDto savedDeal = dealService.saveDeal(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeal);
    }
}