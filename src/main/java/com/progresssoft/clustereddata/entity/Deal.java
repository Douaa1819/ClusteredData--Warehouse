package com.progresssoft.clustereddata.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deals")
public class Deal {

    @Id
    private String dealUniqueId;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    @Column(name = "from_currency", nullable = false, length = 3)
    @Size(min = 3, max = 3)
    @NotNull
    private String fromCurrencyCode;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid currency code format")
    @Column(name = "to_currency", nullable = false, length = 3)
    @Size(min = 3, max = 3)
    @NotNull
    private String toCurrency;

    @PastOrPresent
    @Column(name = "deal_timestamp", nullable = false)
    @NotNull
    private LocalDateTime dealTimestamp;

    @Positive
    @Column(name = "deal_amount", nullable = false)
    @NotNull
    private BigDecimal dealAmount;
}