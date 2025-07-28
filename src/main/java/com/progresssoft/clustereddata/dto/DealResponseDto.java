package com.progresssoft.clustereddata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DealResponseDto(
        @NotBlank(message = "Deal ID is required") String id,

        @NotNull(message = "From currency is required") String fromCurrencyCode,

        @NotNull(message = "To currency is required") String toCurrency,
        @NotNull LocalDateTime dealTimestamp,
        @NotNull(message = "Deal amount is required")
        @Positive(message = "Deal amount must be positive") BigDecimal dealAmount) {
}
