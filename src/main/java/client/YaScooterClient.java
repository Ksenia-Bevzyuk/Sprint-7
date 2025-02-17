package client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.Courier;
import model.Credentials;
import model.Order;
import model.Track;

import static io.restassured.RestAssured.given;

public class YaScooterClient {

    RequestSpecification requestSpec;

    public YaScooterClient() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru")
                .setContentType("application/json");
        requestSpec = builder.build();
    }

    @Step("Создать курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .body(courier)
                .post(EndPoints.CREATE_COURIER)
                .then()
                .log()
                .all();
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return
                given()
                        .filter(new AllureRestAssured())
                        .log()
                        .all()
                        .spec(requestSpec)
                        .body(credentials)
                        .post(EndPoints.LOGIN_COURIER)
                        .then()
                        .log()
                        .all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(Credentials credentials) {
        ValidatableResponse response = loginCourier(credentials);
        int id = response.extract().jsonPath().getInt("id");
        return given()
                .log()
                .all()
                .spec(requestSpec)
                .delete(EndPoints.DELETE_COURIER + id)
                .then()
                .log()
                .all();
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .body(order)
                .post(EndPoints.CREATE_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(ValidatableResponse response) {
        int trackNumber = response.extract().jsonPath().getInt("track");
        Track track = new Track(trackNumber);
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .body(track)
                .put(EndPoints.CANCEL_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получить список заказов с query-параметром limit")
    public ValidatableResponse getOrdersWithLimit() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .queryParam("limit", "2")
                .get(EndPoints.GET_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получить список заказов с query-параметром page")
    public ValidatableResponse getOrdersWithPage() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .queryParam("page", "1")
                .get(EndPoints.GET_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получить список заказов с query-параметром station")
    public ValidatableResponse getOrdersWithStation() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .queryParam("nearestStation", "[\"1\"]")
                .get(EndPoints.GET_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получить список заказов с query-параметрами limit&page")
    public ValidatableResponse getOrdersWithLimitAndPage() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .queryParam("limit", "2")
                .queryParam("page", "1")
                .get(EndPoints.GET_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получить список заказов с query-параметрами limit&page&station")
    public ValidatableResponse getOrdersWithLimitAndPageAndStation() {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .spec(requestSpec)
                .queryParam("limit", "2")
                .queryParam("page", "1")
                .queryParam("nearestStation", "[\"1\"]")
                .get(EndPoints.GET_ORDER)
                .then()
                .log()
                .all();
    }
}