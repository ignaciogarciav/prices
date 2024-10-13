package com.gft.demo.prices.infrastructure.out.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRICES")
public class PriceEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "BRAND_ID")
    private Long brandId;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "START_DATE")
    private OffsetDateTime startDate;
    @Column(name = "END_DATE")
    private OffsetDateTime endDate;
}

