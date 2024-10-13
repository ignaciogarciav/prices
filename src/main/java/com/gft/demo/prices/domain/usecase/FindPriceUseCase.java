package com.gft.demo.prices.domain.usecase;

import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.domain.model.PriceCriteria;
import jakarta.validation.Valid;

public interface FindPriceUseCase {
    Price findOneByCriteria(@Valid PriceCriteria priceCriteria);
}
