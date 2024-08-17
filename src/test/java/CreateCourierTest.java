import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CreateCourierTest {

    private CourierManager courierManager;
    private Courier courier;
    @Before
    public void setUp(){
        courierManager = new CourierManager();
        courier = courierManager.createCourierData();
    }
    @Test
    @DisplayName("Создание курьера")
    public void createCourier() {
        // Генерация данных и создание курьера
        Response createResponse = courierManager.createCourier(courier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        createResponse.then().statusCode(201);
        String response = createResponse.getBody().asString();
        assertEquals("{\"ok\":true}", response);
        // Удаление созданного курьера
        int courierId = courierManager.loginCourierAndExtractId(courier);
        courierManager.deleteCourierById(courierId);
    }
    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void createDoubleCourier() {
        // Генерация данных и создание первого курьера
        courierManager.createCourier(courier);
        // Попытка создать второго курьера с тем же логином
        Response createResponseDoubleCourier = courierManager.createCourier(courier);
        // Проверка, что второй запрос возвращает ошибку
        createResponseDoubleCourier.then().statusCode(409);
        String responseBody2 = createResponseDoubleCourier.getBody().asString();
        assertEquals("{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}", responseBody2);
        int courierId = courierManager.loginCourierAndExtractId(courier);
        courierManager.deleteCourierById(courierId);
    }
    @Test
    @DisplayName("Невозможно создать курьера без логина")
    public void createCourierWithoutLogin() {
        //Генерация данных и установка логина на null
        courier.setLogin(null);
        //Попытка создания курьера где login = null
        Response response = courierManager.createCourier(courier);
        response.then().statusCode(400);
        String responseBody = response.getBody().asString();
        assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}",responseBody);

    }
    @Test
    @DisplayName("Невозможно создать курьера без пароля")
    public void createCourierWithoutPassword() {
        // Генерация данных и установка пароля на null
        courier.setPassword(null);

        // Попытка создания курьера с null-паролем
        Response response = courierManager.createCourier(courier);

        // Проверка статуса ответа
        response.then().statusCode(400);

        // Проверка тела ответа
        String responseBody = response.getBody().asString();
        assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}", responseBody);
    }
    @Test
    @DisplayName("Возможно создать курьера без имени")
    public void createCourierWithoutFirstname() {
        // Генерация данных и создание курьера
        courier.setFirstName(null);
        Response response = courierManager.createCourier(courier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        response.then().statusCode(201);
        String responseBody = response.getBody().asString();
        assertEquals("{\"ok\":true}", responseBody);
        // Удаление созданного курьера
        int courierId = courierManager.loginCourierAndExtractId(courier);
        courierManager.deleteCourierById(courierId);

    }
}
