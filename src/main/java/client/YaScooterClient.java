package client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.Credentials;
import static io.restassured.RestAssured.given;

public class YaScooterClient {
    private String baseURI;

    public YaScooterClient(String baseURI) {
        this.baseURI = baseURI;
    }
    @Step("Клиент: создать курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .filter(new AllureRestAssured())
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier")
                .then()
                .log()
                .all();
    }

    @Step("Клиент: логин курьера")
    public ValidatableResponse loginCourier(Credentials credentials) {
        return
                given()
                        .filter(new AllureRestAssured())
                        .log()
                        .all()
                        .baseUri(baseURI)
                        .header("Content-Type", "application/json")
                        .body(credentials)
                        .post("/api/v1/courier/login")
                        .then()
                        .log()
                        .all();
    }

    @Step("Клиент: удаление курьера")
    public ValidatableResponse deleteCourier(Credentials credentials) {
        ValidatableResponse response = loginCourier(credentials);
        int id = response.extract().jsonPath().getInt("id");
        String jsonDel = "{\"id\": \"Integer.toString(id)\"}";
        return given()
                .log()
                .all()
                .baseUri(baseURI)
                .header("Content-Type", "application/json")
                .body(jsonDel)
                .delete("/api/v1/courier/" + id)
                .then()
                .log()
                .all();
    }
}