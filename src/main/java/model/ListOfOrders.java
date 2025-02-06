package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListOfOrders {
    private String baseURI;

    private String id;
    private String courierId;

    public ListOfOrders(String id, String courierId) {
        this.id = id;
        this.courierId = courierId;
    }

    public ListOfOrders(String baseURI) {
        this.baseURI = baseURI;
    }

    public String getBaseURI() {
        return baseURI;
    }

    public ListOfOrders() {
    }

    public String getId() {
        return id;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public ValidatableResponse getOrdersWithLimit() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("limit", "2")
                .get("/api/v1/orders")
                .then()
                .log()
                .all();
    }

    public ValidatableResponse getOrdersWithPage() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("page", "1")
                .get("/api/v1/orders")
                .then()
                .log()
                .all();
    }

    public ValidatableResponse getOrdersWithStation() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("nearestStation", "[\"1\"]")
                .get("/api/v1/orders")
                .then()
                .log()
                .all();
    }

    public ValidatableResponse getOrdersWithLimitAndPage() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("limit", "2")
                .queryParam("page", "1")
                .get("/api/v1/orders")
                .then()
                .log()
                .all();
    }

    public ValidatableResponse getOrdersWithLimitAndPageAndStation() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .queryParam("limit", "2")
                .queryParam("page", "1")
                .queryParam("nearestStation", "[\"1\"]")
                .get("/api/v1/orders")
                .then()
                .log()
                .all();
    }
}