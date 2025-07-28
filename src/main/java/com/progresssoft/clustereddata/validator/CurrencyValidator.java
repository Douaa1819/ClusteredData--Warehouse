package com.progresssoft.clustereddata.validator;


    import com.progresssoft.clustereddata.dto.DealRequestDto;
    import com.progresssoft.clustereddata.exception.CurrencyNotAvailableException;
    import com.progresssoft.clustereddata.exception.InvalidCurrencyException;
    import com.progresssoft.clustereddata.shared.CurrencyHolder;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyValidator {

        private final CurrencyHolder currencyHolder;

        public void validateCurrencyRules(DealRequestDto dto) {
            validateDifferentCurrencies(dto);
            validateCurrencyExists(dto.fromCurrencyCode());
            validateCurrencyExists(dto.toCurrency());
        }

        private void validateDifferentCurrencies(DealRequestDto dto) {
            if (dto.fromCurrencyCode().equalsIgnoreCase(dto.toCurrency())) {
                String message = String.format("Invalid deal: fromCurrency [%s] and toCurrency [%s] cannot be the same.",
                        dto.fromCurrencyCode(), dto.toCurrency());
                log.warn(message);
                throw new InvalidCurrencyException(message);
            }
        }

        private void validateCurrencyExists(String currency) {
            if (!currencyHolder.exists(currency)) {
                String message = String.format("Currency not available: %s", currency);
                log.warn(message);
                throw new CurrencyNotAvailableException(message);
            }
        }
    }



