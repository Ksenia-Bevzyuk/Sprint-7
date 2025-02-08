import client.YaScooterClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ListOfOrders;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderListTest {
    private YaScooterClient client;

    @Before
    public void before() {
        client = new YaScooterClient();
    }

    @Test
    @DisplayName("GET /api/v1/orders query limit")
    @Description("Запрос списка заказов с query limit возвращает в теле список заказов")
    public void orderListWithQueryLimitSuccess() {

        ValidatableResponse response = client.getOrdersWithLimit();
        assertTrue(response.extract().statusCode() == SC_OK);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query page")
    @Description("Запрос списка заказов с query page возвращает в теле список заказов")
    public void orderListWithQueryPageSuccess() {

        ValidatableResponse response = client.getOrdersWithPage();
        assertTrue(response.extract().statusCode() == SC_OK);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query station")
    @Description("Запрос списка заказов с query station возвращает в теле список заказов")
    public void orderListWithQueryStationSuccess() {

        ValidatableResponse response = client.getOrdersWithStation();
        assertTrue(response.extract().statusCode() == SC_OK);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query limit&page")
    @Description("Запрос списка заказов с query limit&page возвращает в теле список заказов")
    public void orderListWithQueryLimitAndPageSuccess() {

        ValidatableResponse response = client.getOrdersWithLimitAndPage();
        assertTrue(response.extract().statusCode() == SC_OK);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query limit&page&station")
    @Description("Запрос списка заказов с query limit&page&station возвращает в теле список заказов")
    public void orderListWithQueryLimitAndPageAndStationSuccess() {

        ValidatableResponse response = client.getOrdersWithLimitAndPageAndStation();
        assertTrue(response.extract().statusCode() == SC_OK);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }
}