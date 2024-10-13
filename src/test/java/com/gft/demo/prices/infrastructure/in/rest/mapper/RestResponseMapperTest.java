package com.gft.demo.prices.infrastructure.in.rest.mapper;

import org.junit.jupiter.api.Test;

import static com.gft.demo.prices.factory.PriceTestFactory.buildPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RestResponseMapperTest {

    RestResponseMapper sut = new RestResponseMapper();

    @Test
    void fromDomainModel() {
        final var price = buildPrice();
        final var result = sut.fromDomainModel(price);

        assertEquals(price.getProductId(), result.getProductId());
        assertEquals(price.getBrandId(), result.getBrandId());
        assertEquals(price.getStartDate(), result.getStartDate());
        assertEquals(price.getEndDate(), result.getEndDate());
        assertEquals(price.getPrice(), result.getApplicablePrice());
        assertEquals(price.getCurrency(), result.getCurrency());
    }
}