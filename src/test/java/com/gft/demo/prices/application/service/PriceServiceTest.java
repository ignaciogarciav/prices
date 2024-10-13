package com.gft.demo.prices.application.service;

import com.gft.demo.prices.domain.exception.PriceNotFoundException;
import com.gft.demo.prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gft.demo.prices.factory.PriceTestFactory.buildPrice;
import static com.gft.demo.prices.factory.PriceTestFactory.buildPriceCriteria;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    PriceRepository priceRepository;
    @InjectMocks
    PriceService sut;

    @Test
    void findOneByCriteriaOk() {
        final var criteria = buildPriceCriteria();
        final var expectedPrice = buildPrice();
        given(priceRepository.findPriceBy(criteria)).willReturn(Optional.of(expectedPrice));
        final var result = sut.findOneByCriteria(criteria);
        assertEquals(expectedPrice, result);
    }

    @Test
    void findOneByCriteriaNotFound() {
        final var criteria = buildPriceCriteria();
        given(priceRepository.findPriceBy(criteria)).willReturn(Optional.empty());
        assertThrows(PriceNotFoundException.class, () -> sut.findOneByCriteria(criteria));
    }
}