import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] color;
    private OrderDataGenerator orderDataGenerator;
    private OrderManager orderManager;
    private Order order;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Before
    public void setUp(){
        orderDataGenerator = new OrderDataGenerator();
        orderManager = new OrderManager();
        order = orderDataGenerator.generateOrderData();
    }
    @Parameterized.Parameters(name = "Тест с цветом: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},      // Тест с цветом BLACK
                {new String[]{"GREY"}},       // Тест с цветом GREY
                {new String[]{"BLACK", "GREY"}},  // Тест с обоими цветами
                {null},                       // Тест без указания цвета
        });
    }
    @Test
    @DisplayName("Создание заказа с параметризацией цвета")
    public void createOrderWithColor() {
        order.setColor(color);
        Response createResponse = orderManager.createOrder(order);
        orderManager.assertResponseContainsTrack(createResponse);
    }
}
