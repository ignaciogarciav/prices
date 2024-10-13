package com.gft.demo.prices.infrastructure.out.db.repository;

import com.gft.demo.prices.infrastructure.out.db.mapper.PriceMapper;
import com.gft.demo.prices.infrastructure.out.db.mapper.SpecificationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import java.time.OffsetDateTime;

import static com.gft.demo.prices.factory.PriceTestFactory.buildPriceCriteria;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@DataJpaTest
@Import({PriceMapper.class, SpecificationMapper.class, PriceSqlRepository.class})
class PriceSqlRepositoryTest {

    @SpyBean
    PriceMapper priceMapper;
    @SpyBean
    SpecificationMapper specificationMapper;
    @Autowired
    PriceSqlRepository sut;

    @Test
    void findPriceByOk() {
        final var criteria = buildPriceCriteria(1L, 35455L, OffsetDateTime.parse("2020-06-14T15:00:00Z"));
        final var result = sut.findPriceBy(criteria);
        assertFalse(result.isEmpty());
        then(specificationMapper).should().toSpec(criteria);
        then(priceMapper).should().toDomainModel(any());
    }

    @Test
    void findPriceByEmptyResult() {
        final var criteria = buildPriceCriteria();
        final var result = sut.findPriceBy(criteria);
        assertTrue(result.isEmpty());
        then(specificationMapper).should().toSpec(criteria);
        then(priceMapper).shouldHaveNoInteractions();
    }
}