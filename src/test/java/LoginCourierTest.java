import client.YaScooterClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.Credentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

public class LoginCourierTest {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private Courier courier;
    private YaScooterClient client;
    private String genLogin;
    ValidatableResponse response;

    @Before
    public void before() {
        genLogin = Courier.generationLogin();

        courier = new Courier(genLogin, "1234", "Sam");
        client = new YaScooterClient(BASE_URI);
        response = client.createCourier(courier);
        assumeTrue(response.extract().statusCode() == 201);
    }

    @Test
    @DisplayName("POST /api/v1/courier/login передача логина и пароля")
    @Description("При передаче обязательных полей в запросе возвращается id курьера")
    public void courierLoginSuccess() {
        Credentials credentials = Credentials.fromCourier(courier);
        client.loginCourier(credentials).assertThat().statusCode(200).body("id", notNullValue());
    }

    @Test
    @DisplayName("POST /api/v1/courier/login несуществующий пароль")
    @Description("При передаче существующего логина и несуществующего пароля код 404")
    public void courierLoginWithNonExistentPass() {
        ValidatableResponse responseTest = client.loginCourier(new Credentials(genLogin, "4321"));
        int statusCode = responseTest.extract().statusCode();
        assertEquals("Ожидается код ответа 404",404, statusCode);
        String body = responseTest.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение","Учетная запись не найдена", body);
    }

    @Test
    @DisplayName("POST /api/v1/courier/login несуществующий логин")
    @Description("При передаче несуществующего логина код 404")
    public void courierLoginWithNonExistentLogin() {
        ValidatableResponse responseTest = client.loginCourier(new Credentials("№%:;", "1234"));
        int statusCode = responseTest.extract().statusCode();
        assertEquals("Ожидается код ответа 404",404, statusCode);
        String body = responseTest.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение","Учетная запись не найдена", body);
    }

    @Test
    @DisplayName("POST /api/v1/courier/login пустой логин")
    @Description("При передаче пустого логина код 400")
    public void courierLoginWithNullLogin() {
        ValidatableResponse responseTest = client.loginCourier(new Credentials(null, "1234"));
        int statusCode = responseTest.extract().statusCode();
        assertEquals("Ожидается код ответа 400",400, statusCode);
        String body = responseTest.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение","Недостаточно данных для входа", body);
    }

    @Test
    @DisplayName("POST /api/v1/courier/login пустой пароль")
    @Description("При передаче пустого пароля код 400")
    public void courierLoginWithNullPass() {
        ValidatableResponse responseTest = client.loginCourier(new Credentials(genLogin, ""));
        int statusCode = responseTest.extract().statusCode();
        assertEquals("Ожидается код ответа 400",400, statusCode);
        String body = responseTest.extract().jsonPath().getString("message");
        assertEquals("Ожидается другое сообщение","Недостаточно данных для входа", body);
    }

    @After
    @DisplayName("DELETE /api/v1/courier/:id")
    @Description("Удаление записей о курьерах после каждого теста")
    public void deleteCourier() {
        if (response.extract().statusCode() == 201) {
            Credentials credentials = Credentials.fromCourier(courier);
            YaScooterClient client = new YaScooterClient(BASE_URI);
            response = client.deleteCourier(credentials);
            client.loginCourier(credentials).assertThat().statusCode(404);
        }
    }
}