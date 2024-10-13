package com.gft.demo.prices.domain.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceCriteria {
    @NotNull
    @Min(value = 1)
    private Long brandId;
    @NotNull
    @Min(value = 1)
    private Long productId;
    @NotNull
    private OffsetDateTime applicationDate;

}
