package com.gft.demo.prices.infrastructure.in.rest.controller;

import com.gft.demo.prices.application.service.PriceService;
import com.gft.demo.prices.domain.exception.PriceNotFoundException;
import com.gft.demo.prices.domain.model.PriceCriteria;
import com.gft.demo.prices.infrastructure.in.rest.mapper.RestResponseMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;

import static com.gft.demo.prices.factory.PriceTestFactory.buildPrice;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestResponseMapper.class)
@WebMvcTest(controllers = PriceController.class)
class PriceControllerTest {

    @MockBean
    PriceService priceService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void priceRetrievalByOk() throws Exception {
        final var price = buildPrice();
        final var brandId = price.getBrandId();
        final var productId = price.getProductId();
        final var offsetDateTime = price.getStartDate().plusDays(1L);
        given(priceService.findOneByCriteria(new PriceCriteria(brandId, productId, offsetDateTime))).willReturn(price);

        mockMvc.perform(get("/prices")
                        .queryParam("brandId", String.valueOf(brandId))
                        .queryParam("productId", String.valueOf(productId))
                        .queryParam("applicationDate", offsetDateTime.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(brandId))
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.applicablePrice").value(price.getPrice()))
                .andExpect(jsonPath("$.currency").value(price.getCurrency()));
    }

    @Test
    void priceRetrievalByNotFound() throws Exception {
        final var brandId = 1L;
        final var productId = 1L;
        final var offsetDateTime = OffsetDateTime.now();
        given(priceService.findOneByCriteria(new PriceCriteria(brandId, productId, offsetDateTime)))
                .willThrow(PriceNotFoundException.class);

        mockMvc.perform(get("/prices")
                        .queryParam("brandId", String.valueOf(brandId))
                        .queryParam("productId", String.valueOf(productId))
                        .queryParam("applicationDate", offsetDateTime.toString()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(PriceNotFoundException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.message").value("Entity not found by given parameters"))
                .andExpect(jsonPath("$.details").doesNotExist());
    }

    @Test
    void priceRetrievalByInvalidParameter() throws Exception {
        mockMvc.perform(get("/prices")
                        .queryParam("brandId", "0")
                        .queryParam("productId", "1")
                        .queryParam("applicationDate", OffsetDateTime.now().toString()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(ConstraintViolationException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.message").value("Provided parameter/s are invalid"))
                .andExpect(jsonPath("$.details[0]").value("priceRetrievalBy.brandId: must be greater than or equal to 1"));

        then(priceService).shouldHaveNoInteractions();
    }

    @Test
    void priceRetrievalByMissingParameter() throws Exception {
        mockMvc.perform(get("/prices")
                        .queryParam("brandId", "1")
                        .queryParam("productId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MissingServletRequestParameterException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.message").value("Mandatory parameter is not provided"))
                .andExpect(jsonPath("$.details[0]")
                        .value("Required request parameter 'applicationDate' for method parameter type OffsetDateTime is not present"));

        then(priceService).shouldHaveNoInteractions();
    }

    @Test
    void priceRetrievalByTypeMismatch() throws Exception {
        mockMvc.perform(get("/prices")
                        .queryParam("brandId", String.valueOf("1"))
                        .queryParam("productId", String.valueOf("1"))
                        .queryParam("applicationDate", "invalidDate"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentTypeMismatchException.class, result.getResolvedException()))
                .andExpect(jsonPath("$.message").value("Provided parameter does not match expected type"))
                .andExpect(jsonPath("$.details[0]")
                        .value(containsString("Failed to convert value of type 'java.lang.String' to required type 'java.time.OffsetDateTime'")));

        then(priceService).shouldHaveNoInteractions();
    }
}