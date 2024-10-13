package com.gft.demo.prices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private Long id;
    private Long brandId;
    private Long productId;
    private BigDecimal price;
    private String currency;
    private Integer priority;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
}
