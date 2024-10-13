package com.gft.demo.prices.factory;

import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.domain.model.PriceCriteria;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class PriceTestFactory {

    private static final Random RANDOM_GENERATOR = new Random();
    private static final Supplier<Long> RANDOM_LONG_SUPPLIER = () -> RANDOM_GENERATOR.nextLong(1, 99);

    public static Price buildPrice() {
        return Price.builder()
                .id(RANDOM_LONG_SUPPLIER.get())
                .brandId(RANDOM_LONG_SUPPLIER.get())
                .productId(RANDOM_LONG_SUPPLIER.get())
                .price(BigDecimal.valueOf(RANDOM_LONG_SUPPLIER.get()))
                .currency(UUID.randomUUID().toString())
                .priority(RANDOM_GENERATOR.nextInt())
                .startDate(OffsetDateTime.now().minusDays(1))
                .endDate(OffsetDateTime.now().plusDays(1))
                .build();
    }

    public static PriceCriteria buildPriceCriteria() {
        return buildPriceCriteria(RANDOM_LONG_SUPPLIER.get(), RANDOM_LONG_SUPPLIER.get(), OffsetDateTime.now());
    }

    public static PriceCriteria buildPriceCriteria(final Long brandId, final Long productId, final OffsetDateTime applicationDate) {
        return PriceCriteria.builder()
                .brandId(brandId)
                .productId(productId)
                .applicationDate(applicationDate)
                .build();
    }
}
