import client.YaScooterClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    private Order order;
    private YaScooterClient client;
    private ValidatableResponse response;

    public CreateOrderTest(String firstName, String lastName, String address,
                           String metroStation, String phone, int rentTime,
                           String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные для создания заказа: {0} {1} {2} {3}")
    public static Object[][] getDataForOrder() {
        return new Object[][] {
                {"Александра", "Алексанова", "ул. Арбат, д. 10 кв. 1",
                        "36", "89123456789", 1, "10.02.2026", "Не звонить", new String[]{"BLACK"}},
                {"Мария", "Марьина", "наб. Косова, д. 16 кв. 7",
                        "2", "80987654321", 2, "08.03.2026", "Захвати шаурму", new String[]{}},
                {"Семен", "Семёнов", "ул. Сходня, д. 103 кв. 34",
                        "21", "89125617154", 3, "10.01.2026", "Скорее", new String[]{"GREY"}},
                {"Павел", "Павлов", "пр. Кирова, д. 7 кв. 46",
                        "2", "81234567890", 4, "11.02.2026", "Долго ждать", new String[]{"BLACK", "GREY"}},
        };
    }

    @Test
    @DisplayName("POST /api/v1/orders создание заказа")
    @Description("Успешное создание заказа с параметрами данных для создания заказа")
    public void createOrderTest() {
        order = new Order(firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
        client = new YaScooterClient();
        response =
                client.createOrder(order);
        response.assertThat().statusCode(SC_CREATED).body("track", notNullValue());
    }

    @After
    public void cancelOrder() {
        ValidatableResponse responseCancel = client.cancelOrder(response);
        assertTrue(responseCancel.extract().statusCode() == SC_OK);
    }
}