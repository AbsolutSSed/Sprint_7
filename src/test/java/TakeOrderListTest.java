import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TakeOrderListTest {
    private OrderManager orderManager;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderManager = new OrderManager();
    }
    @Test
    @DisplayName("Получение списка заказов с пустым Courier-Id")
    public void getOrderListWithoutCourierId() {
        Response response = orderManager.getOrdersList();
        response.then().statusCode(200);
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
        List<?> ordersList = response.jsonPath().getList("orders");
        assertTrue("Проверка наличия массива по ключу 'orders' в ответе ", ordersList != null && !ordersList.isEmpty());
    }
}
