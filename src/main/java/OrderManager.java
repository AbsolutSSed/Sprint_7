import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderManager {
    private static final String API_PATH_ORDER = "/api/v1/orders";
    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Concent-type","application/json")
                .body(order)
                .when()
                .post(API_PATH_ORDER);
    }
    public void assertResponseContainsTrack(Response response) {
        response.then().statusCode(201);
        String track = response.jsonPath().getString("track");
    }
    @Step("Получение списка заказов")
    public Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(API_PATH_ORDER);
    }

}
