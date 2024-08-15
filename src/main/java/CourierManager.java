import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class CourierManager {
private CourierDataGenerator courierDataGenerator = new CourierDataGenerator();
private Gson gson = new Gson();

    private static final String API_PATH_CREATE = "/api/v1/courier";
    private static final String API_PATH_LOGIN = "/api/v1/courier/login";
    private static final String API_PATH_DELETE = "/api/v1/courier/";
    @Step("Генерация данных курьера")
    public Courier createCourierData() {
        Courier courier = courierDataGenerator.generateCourierData();
        return courier;
    }
    @Step("Создание курьера")
    public Response createCourier(Courier courier) {

        Response createResponse = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(API_PATH_CREATE);
        return createResponse;
    }
    @Step("Вход в учетную запись курьера")
    public Response loginCourier(Courier courier) {
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(API_PATH_LOGIN);
        return loginResponse;
    }
    @Step("Вход в учетную запись курьера для получения ID курьера")
    public int loginCourierAndExtractId(Courier courier) {
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(API_PATH_LOGIN);
        loginResponse.then().statusCode(200);
        CourierResponses idResponse = gson.fromJson(loginResponse.getBody().asString(), CourierResponses.class);
        int courierId = idResponse.getId();
        return courierId;
    }

    @Step("Удаление созданного курьера")
    public void deleteCourierById(int courierId) {
        System.out.println("Попытка удалить курьера с ID: " + courierId);
        Response deleteResponse = given()
                .header("Content-type", "application/json")
                .when()
                .delete(API_PATH_DELETE + courierId);
        System.out.println("Ответ на запрос удаления: " + deleteResponse.getBody().asString());
        deleteResponse.then().statusCode(200);
    }

}

