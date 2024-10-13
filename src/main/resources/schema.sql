DROP TABLE IF EXISTS PRICES;

CREATE TABLE PRICES
(
    ID         SERIAL PRIMARY KEY,
    BRAND_ID   BIGINT NOT NULL,
    PRODUCT_ID BIGINT,
    PRICE      DECIMAL(10, 2),
    CURRENCY   VARCHAR(50),
    PRIORITY   INTEGER,
    START_DATE TIMESTAMP WITH TIME ZONE,
    END_DATE   TIMESTAMP WITH TIME ZONE
);
