package com.gft.demo.prices.infrastructure.in.rest.controller;

import com.gft.demo.prices.domain.model.PriceCriteria;
import com.gft.demo.prices.domain.usecase.FindPriceUseCase;
import com.gft.demo.prices.infrastructure.in.rest.api.PricesApi;
import com.gft.demo.prices.infrastructure.in.rest.mapper.RestResponseMapper;
import com.gft.demo.prices.infrastructure.in.rest.model.PriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final FindPriceUseCase priceService;
    private final RestResponseMapper mapper;

    @Override
    public ResponseEntity<PriceResponse> priceRetrievalBy(final Long brandId, final Long productId,
                                                          final OffsetDateTime applicationDate) {
        return ResponseEntity.ok(mapper.fromDomainModel(
                priceService.findOneByCriteria(PriceCriteria.builder().brandId(brandId)
                        .productId(productId).applicationDate(applicationDate).build())
        ));
    }
}
