import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ListOfOrders;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeTrue;


public class OrderListTest {
    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private ListOfOrders orders;

    @Before
    public void before() {
        orders = new ListOfOrders(BASE_URI);
    }

    @Test
    @DisplayName("GET /api/v1/orders query limit")
    @Description("Запрос списка заказов с query limit возвращает в теле список заказов")
    public void orderListWithQueryLimitSuccess() {

        ValidatableResponse response = orders.getOrdersWithLimit();
        assumeTrue(response.extract().statusCode() == 200);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query page")
    @Description("Запрос списка заказов с query page возвращает в теле список заказов")
    public void orderListWithQueryPageSuccess() {

        ValidatableResponse response = orders.getOrdersWithPage();
        assumeTrue(response.extract().statusCode() == 200);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query station")
    @Description("Запрос списка заказов с query station возвращает в теле список заказов")
    public void orderListWithQueryStationSuccess() {

        ValidatableResponse response = orders.getOrdersWithStation();
        assumeTrue(response.extract().statusCode() == 200);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query limit&page")
    @Description("Запрос списка заказов с query limit&page возвращает в теле список заказов")
    public void orderListWithQueryLimitAndPageSuccess() {

        ValidatableResponse response = orders.getOrdersWithLimitAndPage();
        assumeTrue(response.extract().statusCode() == 200);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }

    @Test
    @DisplayName("GET /api/v1/orders query limit&page&station")
    @Description("Запрос списка заказов с query limit&page&station возвращает в теле список заказов")
    public void orderListWithQueryLimitAndPageAndStationSuccess() {

        ValidatableResponse response = orders.getOrdersWithLimitAndPageAndStation();
        assumeTrue(response.extract().statusCode() == 200);

        List<ListOfOrders> orders = response.extract().jsonPath()
                .getList("orders", ListOfOrders.class);
        assertFalse(orders.isEmpty());
    }
}