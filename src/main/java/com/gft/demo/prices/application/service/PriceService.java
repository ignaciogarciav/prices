package com.gft.demo.prices.application.service;

import com.gft.demo.prices.domain.exception.PriceNotFoundException;
import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.domain.model.PriceCriteria;
import com.gft.demo.prices.domain.repository.PriceRepository;
import com.gft.demo.prices.domain.usecase.FindPriceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PriceService implements FindPriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price findOneByCriteria(@Valid final PriceCriteria priceCriteria) {
        return priceRepository.findPriceBy(priceCriteria).orElseThrow(PriceNotFoundException::new);
    }
}

