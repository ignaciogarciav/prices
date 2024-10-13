package com.gft.demo.prices.infrastructure.out.db.repository;

import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.domain.model.PriceCriteria;
import com.gft.demo.prices.domain.repository.PriceRepository;
import com.gft.demo.prices.infrastructure.out.db.mapper.PriceMapper;
import com.gft.demo.prices.infrastructure.out.db.mapper.SpecificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.domain.Sort.by;

@Repository
@RequiredArgsConstructor
public class PriceSqlRepository implements PriceRepository {

    private static final String PRICE_ENTITY_PRIORITY_KEY = "priority";

    private final PriceMapper priceMapper;
    private final PriceJpaRepository priceJpaRepository;
    private final SpecificationMapper specificationMapper;

    @Override
    public Optional<Price> findPriceBy(final PriceCriteria priceCriteria) {
        return priceJpaRepository.findBy(
                        specificationMapper.toSpec(priceCriteria),
                        fluentQuery -> fluentQuery.sortBy(by(Sort.Direction.DESC, PRICE_ENTITY_PRIORITY_KEY)).first())
                .map(priceMapper::toDomainModel);
    }
}
