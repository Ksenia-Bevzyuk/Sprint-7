import client.YaScooterClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.Credentials;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;


public class CreateCourierTest {

    private Courier courier;
    private ValidatableResponse response;

    @Test
    @DisplayName("POST /api/v1/courier корректно заполнены все поля")
    @Description("Успешное создание курьера, заполнены все поля")
    public void createCourierAllFieldsTest() {
        String genLogin = Courier.generationLogin();

        courier = new Courier(genLogin, "1234", "Sam");
        YaScooterClient client = new YaScooterClient();
        response = client.createCourier(courier);
        response.assertThat().statusCode(SC_CREATED).body("ok", equalTo(true));
    }

    @Test
    @DisplayName("POST /api/v1/courier корректно заполнены только обязательные поля")
    @Description("Успешное создание курьера, заполнены только обязательные поля")
    public void createCourierRequiredFieldsTest() {
        String genLogin = Courier.generationLogin();

        courier = new Courier(genLogin, "1234");
        YaScooterClient client = new YaScooterClient();
        response = client.createCourier(courier);
        response.assertThat().statusCode(SC_CREATED).body("ok", equalTo(true));
    }

    @Test
    @DisplayName("POST /api/v1/courier корректный логин, пароль null")
    @Description("Код 400 при создании курьера только с полем логин, пароль - null")
    public void createCourierWithFieldLoginOnlyStatusCode400Test() {
        String genLogin = Courier.generationLogin();

        courier = new Courier(genLogin,null);
        YaScooterClient client = new YaScooterClient();
        response = client.createCourier(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Ожидается код ответа 400", SC_BAD_REQUEST, statusCode);
        String body = response.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение","Недостаточно данных для создания учетной записи", body);
    }

    @Test
    @DisplayName("POST /api/v1/courier корректный пароль, логин null")
    @Description("Код 400 при создании курьера только с полем пароль, логин - null")
    public void createCourierWithFieldPassOnlyStatusCode400Test() {
        String genLogin = Courier.generationLogin();

        courier = new Courier(null,"1234");
        YaScooterClient client = new YaScooterClient();
        response = client.createCourier(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Ожидается код ответа 400", SC_BAD_REQUEST, statusCode);
        String body = response.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение",
                "Недостаточно данных для создания учетной записи", body);
    }

    @Test
    @DisplayName("POST /api/v1/courier попытка создать двух одинаковых курьеров")
    @Description("Код 409 при создании двух одинаковых курьеров")
    public void createTwoIdenticalCourierStatusCode409Test() {
        String genLogin = Courier.generationLogin();

        courier = new Courier(genLogin, "1234", "Sam");

        YaScooterClient client = new YaScooterClient();
        client.createCourier(courier);
        response = client.createCourier(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Ожидается код ответа 409", SC_CONFLICT, statusCode);
        String body = response.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение","Этот логин уже используется", body);
    }

    @After
    @DisplayName("DELETE /api/v1/courier/:id")
    @Description("Удаление записей о курьерах после каждого теста")
    public void deleteCourier() {
        if (response.extract().statusCode() != SC_BAD_REQUEST) {
            Credentials credentials = Credentials.fromCourier(courier);
            YaScooterClient client = new YaScooterClient();
            response = client.deleteCourier(credentials);
            client.loginCourier(credentials).assertThat().statusCode(SC_NOT_FOUND);
        }
    }
}