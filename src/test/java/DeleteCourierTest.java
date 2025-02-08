import client.YaScooterClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.Credentials;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assume.assumeTrue;

public class DeleteCourierTest {
    private Courier courier;
    private YaScooterClient client;
    private String genLogin;
    private Credentials credentials;
    private ValidatableResponse responseCreate;

    @Before
    public void before() {
        genLogin = Courier.generationLogin();

        courier = new Courier(genLogin, "1234", "Sam");
        client = new YaScooterClient();
        responseCreate = client.createCourier(courier);
        assumeTrue(responseCreate.extract().statusCode() == SC_CREATED);
        credentials = Credentials.fromCourier(courier);
        ValidatableResponse responseLogin = client.loginCourier(credentials);
        assumeTrue(responseLogin.extract().statusCode() == SC_OK);
    }
    @Test
    @DisplayName("DELETE /api/v1/courier/:id")
    @Description("Удаление курьера")
    public void deleteCourierTest() {

        ValidatableResponse response = client.deleteCourier(credentials);
        response.assertThat().statusCode(SC_OK).body("ok", equalTo(true));
    }
}