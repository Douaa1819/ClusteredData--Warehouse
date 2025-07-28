package com.progresssoft.clustereddata;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.dto.DealResponseDto;
import com.progresssoft.clustereddata.entity.Deal;
import com.progresssoft.clustereddata.exception.CurrencyNotAvailableException;
import com.progresssoft.clustereddata.exception.InvalidCurrencyException;
import com.progresssoft.clustereddata.exception.RequestAlreadyExistException;
import com.progresssoft.clustereddata.mapper.DealMapper;
import com.progresssoft.clustereddata.repository.DealRepository;
import com.progresssoft.clustereddata.services.impl.DealServiceImpl;
import com.progresssoft.clustereddata.validator.CurrencyValidator;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealMapper dealMapper;

    @Mock
    private DealRepository dealRepository;

    @Mock
    private CurrencyValidator currencyValidator;

    @InjectMocks
    private DealServiceImpl dealService;

    private final DealRequestDto dealRequestDto = new DealRequestDto(
            "DEAL001", "USD", "EUR", BigDecimal.valueOf(1000.00)
    );

    private final Deal deal = new Deal();
    private final DealResponseDto dealResponseDto = new DealResponseDto(
            "DEAL001", "USD", "EUR", LocalDateTime.now(), BigDecimal.valueOf(1000.00)
    );

    @Test
    @DisplayName(" Should save a valid deal successfully")
    void shouldSaveValidDealSuccessfully() {

        given(dealRepository.existsById("DEAL001")).willReturn(false);
        given(dealMapper.toEntity(dealRequestDto)).willReturn(deal);
        given(dealRepository.save(deal)).willReturn(deal);
        given(dealMapper.toResponseEntity(deal)).willReturn(dealResponseDto);
        DealResponseDto result = dealService.saveDeal(dealRequestDto);
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("DEAL001");

        verify(currencyValidator).validateCurrencyRules(dealRequestDto);
        verify(dealRepository).existsById("DEAL001");
        verify(dealMapper).toEntity(dealRequestDto);
        verify(dealRepository).save(deal);
        verify(dealMapper).toResponseEntity(deal);
        verifyNoMoreInteractions(dealRepository, dealMapper, currencyValidator);
    }

    @Test
    @DisplayName(" Should throw exception if deal already exists")
    void shouldThrowIfDealAlreadyExists() {
        given(dealRepository.existsById("DEAL001")).willReturn(true);


        RequestAlreadyExistException ex = assertThrows(
                RequestAlreadyExistException.class,
                () -> dealService.saveDeal(dealRequestDto)
        );


        assertThat(ex.getMessage()).contains("Deal already exists with ID: DEAL001");

        verify(currencyValidator).validateCurrencyRules(dealRequestDto);
        verify(dealRepository).existsById("DEAL001");
        verifyNoMoreInteractions(dealRepository, dealMapper, currencyValidator);
    }

    @Test
    @DisplayName(" Should propagate InvalidCurrencyException from validator")
    void shouldPropagateInvalidCurrencyException() {

        DealRequestDto invalid = new DealRequestDto("ID1", "USD", "USD", BigDecimal.valueOf(100));
        doThrow(new InvalidCurrencyException("Invalid deal: fromCurrency [USD] and toCurrency [USD] cannot be the same."))
                .when(currencyValidator).validateCurrencyRules(invalid);


        InvalidCurrencyException ex = assertThrows(
                InvalidCurrencyException.class,
                () -> dealService.saveDeal(invalid)
        );


        assertThat(ex.getMessage())
                .isEqualTo("Invalid deal: fromCurrency [USD] and toCurrency [USD] cannot be the same.");

        verify(currencyValidator).validateCurrencyRules(invalid);
        verifyNoInteractions(dealRepository, dealMapper);
    }

    @Test
    @DisplayName(" Should throw if fromCurrency is not available")
    void shouldThrowIfFromCurrencyNotAvailable() {
        DealRequestDto dto = new DealRequestDto("ID3", "XXX", "EUR", BigDecimal.valueOf(1000.00));
        doThrow(new CurrencyNotAvailableException("Currency not available: XXX"))
                .when(currencyValidator).validateCurrencyRules(dto);

        CurrencyNotAvailableException ex = assertThrows(
                CurrencyNotAvailableException.class,
                () -> dealService.saveDeal(dto)
        );

        assertThat(ex.getMessage()).isEqualTo("Currency not available: XXX");
        verify(currencyValidator).validateCurrencyRules(dto);
        verifyNoInteractions(dealRepository, dealMapper);
    }

    @Test
    @DisplayName("Should throw if toCurrency is not available")
    void shouldThrowIfToCurrencyNotAvailable() {
        DealRequestDto dto = new DealRequestDto("ID4", "USD", "XXX", BigDecimal.valueOf(1000.00));
        doThrow(new CurrencyNotAvailableException("Currency not available: XXX"))
                .when(currencyValidator).validateCurrencyRules(dto);

        CurrencyNotAvailableException ex = assertThrows(
                CurrencyNotAvailableException.class,
                () -> dealService.saveDeal(dto)
        );

        assertThat(ex.getMessage()).isEqualTo("Currency not available: XXX");
        verify(currencyValidator).validateCurrencyRules(dto);
        verifyNoInteractions(dealRepository, dealMapper);
    }
}
