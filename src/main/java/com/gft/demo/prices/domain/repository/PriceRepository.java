package com.gft.demo.prices.domain.repository;

import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.domain.model.PriceCriteria;

import java.util.Optional;

public interface PriceRepository {
    Optional<Price> findPriceBy(PriceCriteria priceCriteria);
}
