import client.YaScooterClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.Credentials;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assume.assumeTrue;

public class DeleteCourierTest {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private Courier courier;
    private YaScooterClient client;
    private String genLogin;

    private Credentials credentials;

    @Before
    public void before() {
        genLogin = Courier.generationLogin();

        courier = new Courier(genLogin, "1234", "Sam");
        client = new YaScooterClient(BASE_URI);
        ValidatableResponse responseCreate = client.createCourier(courier);
        assumeTrue(responseCreate.extract().statusCode() == 201);
        credentials = Credentials.fromCourier(courier);
        ValidatableResponse responseLogin = client.loginCourier(credentials);
        assumeTrue(responseLogin.extract().statusCode() == 200);
    }
    @Test
    @DisplayName("DELETE /api/v1/courier/:id")
    @Description("Удаление курьера")
    public void deleteCourierTest() {

        ValidatableResponse response = client.deleteCourier(credentials);
        response.assertThat().statusCode(200).body("ok", equalTo(true));
    }
}