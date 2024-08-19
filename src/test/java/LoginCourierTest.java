import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginCourierTest {
    private CourierManager courierManager;
    private Courier courier;
    @Before
    public void setUp(){
        courierManager = new CourierManager();
        courier = courierManager.createCourierData();
    }
    @Test
    @DisplayName("Авторизация курьера")
    public void loginCourier() {
        // Генерация данных и создание курьера
        Response createResponse = courierManager.createCourier(courier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        createResponse.then().statusCode(201);
        String response = createResponse.getBody().asString();
        assertEquals("{\"ok\":true}", response);
        // Вход по данным курьера
        int courierId = courierManager.loginCourierAndExtractId(courier);
        // Удаление курьера
        courierManager.deleteCourierById(courierId);
    }
    @Test
    @DisplayName("Невозможно авторизоваться без логина")
    public void loginCourierWithoutLoginField() {
        // Генерация данных и создание курьера
        Response createResponse = courierManager.createCourier(courier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        createResponse.then().statusCode(201);
        String responseCreate = createResponse.getBody().asString();
        assertEquals("{\"ok\":true}", responseCreate);
        courier.setLogin(null);
        // Проверка статус кода - 400, сравнение с полученным ответом
        Response loginResponse = courierManager.loginCourier(courier);
        loginResponse.then().statusCode(400);
        String responseLogin = loginResponse.getBody().asString();
        assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для входа\"}",responseLogin);
    }
    @Test
    @DisplayName("Невозможно авторизоваться без пароля")
    @Issue("Баг с попыткой логина без пароля, система отдает 504")
    public void loginCourierWithoutPasswordField() {
        // Генерация данных и создание курьера
        Response createResponse = courierManager.createCourier(courier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        createResponse.then().statusCode(201);
        String responseCreate = createResponse.getBody().asString();
        assertEquals("{\"ok\":true}", responseCreate);
        courier.setPassword(null);
        // Проверка статус кода - 400, сравнение с полученным ответом
        Response loginResponse = courierManager.loginCourier(courier);
        loginResponse.then().statusCode(504);
        String responseLogin = loginResponse.getBody().asString();
        assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для входа\"}",responseLogin);
    }
    @Test
    @DisplayName("Невозможно авторизоваться под несуществующими данными")
    public void loginIncorrectCourier() {
        // Генерация данных и попытка залогиниться под ними
        Response loginResponse = courierManager.loginCourier(courier);
        loginResponse.then().statusCode(404);
        String responseLogin = loginResponse.getBody().asString();
        assertEquals("{\"code\":404,\"message\":\"Учетная запись не найдена\"}",responseLogin);
    }
    @Test
    @DisplayName("Невозможно залогиниться под некорректными данными")
    public void loginIncorrectCourierData() {
        // Генерация данных и создание курьера
        Response createResponse = courierManager.createCourier(courier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        createResponse.then().statusCode(201);
        String responseCreate = createResponse.getBody().asString();
        assertEquals("{\"ok\":true}", responseCreate);
        courier.setPassword("Я забыл пароль");
        // Проверка статус кода - 400, сравнение с полученным ответом
        Response loginResponse = courierManager.loginCourier(courier);
        loginResponse.then().statusCode(404);
        String responseLogin = loginResponse.getBody().asString();
        assertEquals("{\"code\":404,\"message\":\"Учетная запись не найдена\"}",responseLogin);
    }
}

