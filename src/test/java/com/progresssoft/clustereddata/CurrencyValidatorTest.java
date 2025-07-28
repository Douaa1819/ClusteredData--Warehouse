package com.progresssoft.clustereddata;

import com.progresssoft.clustereddata.dto.DealRequestDto;
import com.progresssoft.clustereddata.exception.CurrencyNotAvailableException;
import com.progresssoft.clustereddata.exception.InvalidCurrencyException;
import com.progresssoft.clustereddata.shared.CurrencyHolder;
import com.progresssoft.clustereddata.validator.CurrencyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyValidatorTest {

    @Mock
    private CurrencyHolder currencyHolder;

    private CurrencyValidator currencyValidator;

    @BeforeEach
    void setUp() {
        currencyValidator = new CurrencyValidator(currencyHolder);
    }

    @Test
    void shouldThrowExceptionWhenCurrenciesAreTheSame() {
        DealRequestDto dto = new DealRequestDto("ID1", "USD", "USD", BigDecimal.TEN);

        InvalidCurrencyException ex = assertThrows(InvalidCurrencyException.class,
                () -> currencyValidator.validateCurrencyRules(dto));

        assertThat(ex.getMessage()).isEqualTo("Invalid deal: fromCurrency [USD] and toCurrency [USD] cannot be the same.");
        verifyNoInteractions(currencyHolder);
    }

    @Test
    void shouldThrowWhenFromCurrencyDoesNotExist() {
        DealRequestDto dto = new DealRequestDto("ID2", "XYZ", "EUR", BigDecimal.TEN);
        given(currencyHolder.exists("XYZ")).willReturn(false);

        CurrencyNotAvailableException ex = assertThrows(CurrencyNotAvailableException.class,
                () -> currencyValidator.validateCurrencyRules(dto));

        assertThat(ex.getMessage()).isEqualTo("Currency not available: XYZ");
        verify(currencyHolder).exists("XYZ");
        verifyNoMoreInteractions(currencyHolder);
    }

    @Test
    void shouldThrowWhenToCurrencyDoesNotExist() {
        DealRequestDto dto = new DealRequestDto("ID3", "USD", "ABC", BigDecimal.TEN);

        given(currencyHolder.exists("USD")).willReturn(true);
        given(currencyHolder.exists("ABC")).willReturn(false);

        CurrencyNotAvailableException ex = assertThrows(CurrencyNotAvailableException.class,
                () -> currencyValidator.validateCurrencyRules(dto));

        assertThat(ex.getMessage()).isEqualTo("Currency not available: ABC");
        verify(currencyHolder).exists("USD");
        verify(currencyHolder).exists("ABC");
        verifyNoMoreInteractions(currencyHolder);
    }

    @Test
    void shouldNotThrowWhenCurrenciesAreValid() {
        DealRequestDto dto = new DealRequestDto("ID4", "USD", "EUR", BigDecimal.TEN);

        given(currencyHolder.exists("USD")).willReturn(true);
        given(currencyHolder.exists("EUR")).willReturn(true);

        assertThatCode(() -> currencyValidator.validateCurrencyRules(dto)).doesNotThrowAnyException();

        verify(currencyHolder).exists("USD");
        verify(currencyHolder).exists("EUR");
        verifyNoMoreInteractions(currencyHolder);
    }
}
