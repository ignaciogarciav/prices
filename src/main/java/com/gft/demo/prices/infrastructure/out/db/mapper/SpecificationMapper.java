package com.gft.demo.prices.infrastructure.out.db.mapper;

import com.gft.demo.prices.domain.model.PriceCriteria;
import com.gft.demo.prices.infrastructure.out.db.entity.PriceEntity;
import jakarta.validation.Valid;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
public class SpecificationMapper {

    private static final String PRICE_ENTITY_BRAND_ID_KEY = "brandId";
    private static final String PRICE_ENTITY_PRODUCT_ID_KEY = "productId";
    private static final String PRICE_ENTITY_START_DATE_KEY = "startDate";
    private static final String PRICE_ENTITY_END_DATE_KEY = "endDate";

    public Specification<PriceEntity> toSpec(@Valid final PriceCriteria priceCriteria) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get(PRICE_ENTITY_BRAND_ID_KEY), priceCriteria.getBrandId()),
                criteriaBuilder.equal(root.get(PRICE_ENTITY_PRODUCT_ID_KEY), priceCriteria.getProductId()),
                criteriaBuilder.lessThanOrEqualTo(root.get(PRICE_ENTITY_START_DATE_KEY), priceCriteria.getApplicationDate()),
                criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE_ENTITY_END_DATE_KEY), priceCriteria.getApplicationDate())
        );
    }
}
