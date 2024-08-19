import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TakeOrderListTest {
    private OrderManager orderManager;
    private Response response;

    @Before
    public void setUp() {
        orderManager = new OrderManager();
        response = orderManager.getOrdersList();
    }
    @Test
    @DisplayName("Получение списка заказов с пустым Courier-Id")
    public void getOrderListWithoutCourierId() {
        response.then().statusCode(200);
        List<?> ordersList = response.jsonPath().getList("orders");
        assertTrue("Проверка наличия массива по ключу 'orders' в ответе ", ordersList != null && !ordersList.isEmpty());
    }
}
