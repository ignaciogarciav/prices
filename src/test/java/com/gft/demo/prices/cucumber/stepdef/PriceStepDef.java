package com.gft.demo.prices.cucumber.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PriceStepDef {

    @LocalServerPort
    int port;
    Response response;

    @Given("a valid request")
    public void aValidRequest() {
    }

    @Given("an invalid request")
    public void anInvalidRequest() {
    }

    @When("criteria given is {int}, {int}, {string}")
    public void criteriaGivenIs(final int brandId, final int productId, final String applicationDate) {
        final var requestSpec = given()
                .queryParam("brandId", brandId)
                .queryParam("applicationDate", applicationDate);
        if (productId != -1) {
            requestSpec.queryParam("productId", productId);
        }
        response = requestSpec
                .when()
                .get("http://localhost:%d/api/v1/prices".formatted(port))
                .then()
                .extract()
                .response();
    }

    @Then("system should return a {int} error with message {string}")
    public void systemShouldReturnAErrorWithMessage(final int statusCode, final String errorMessage) {
        assertEquals(statusCode, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().jsonPath().get("message"));
    }

    @Then("system should respond with an applicable price {float} with currency {string}")
    public void systemShouldRespondWithAnApplicablePriceWithCurrency(final Float expectedPrice, final String currency) {
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedPrice, response.getBody().jsonPath().get("applicablePrice"));
        assertEquals(currency, response.getBody().jsonPath().get("currency"));
    }

}
