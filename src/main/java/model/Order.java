package model;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Order {
    private String baseURI;

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order(String firstName, String lastName, String address,
                 String metroStation, String phone, int rentTime,
                 String deliveryDate, String comment, String[] color, String baseURI) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
        this.baseURI = baseURI;
    }

    public Order() {
    }

    public Order(String baseURI) {
        this.baseURI = baseURI;
    }

    public String getBaseURI() {
        return baseURI;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }

    public ValidatableResponse createOrder(Order order) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .body(order)
                .post("/api/v1/orders")
                .then()
                .log()
                .all();
    }

    public ValidatableResponse cancelOrder(Order order) {
        ValidatableResponse response = createOrder(order);
        int track = response.extract().jsonPath().getInt("track");
        String jsonCancel = "{\"track\": " + track+ "}";
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .body(jsonCancel)
                .put("/api/v1/orders/cancel")
                .then()
                .log()
                .all();
    }
}