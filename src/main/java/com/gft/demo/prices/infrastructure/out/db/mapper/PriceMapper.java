package com.gft.demo.prices.infrastructure.out.db.mapper;

import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.infrastructure.out.db.entity.PriceEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public Price toDomainModel(final PriceEntity priceEntity) {
        return Price.builder()
                .id(priceEntity.getId())
                .brandId(priceEntity.getBrandId())
                .productId(priceEntity.getProductId())
                .price(priceEntity.getPrice())
                .currency(priceEntity.getCurrency())
                .priority(priceEntity.getPriority())
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .build();
    }
}
