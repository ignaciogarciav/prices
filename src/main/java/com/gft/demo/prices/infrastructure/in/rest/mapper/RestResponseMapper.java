package com.gft.demo.prices.infrastructure.in.rest.mapper;

import com.gft.demo.prices.domain.model.Price;
import com.gft.demo.prices.infrastructure.in.rest.model.PriceResponse;
import org.springframework.stereotype.Component;

@Component
public class RestResponseMapper {

    public PriceResponse fromDomainModel(final Price source) {
        return new PriceResponse()
                .productId(source.getProductId())
                .brandId(source.getBrandId())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .applicablePrice(source.getPrice())
                .currency(source.getCurrency());
    }
}
