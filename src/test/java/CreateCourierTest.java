import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CreateCourierTest {

    private CourierManager courierManager;
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierManager = new CourierManager();
    }
    @Test
    @DisplayName("Создание курьера")
    public void createCourier() {
        // Генерация данных и создание курьера
        Courier courier = courierManager.createCourierData();
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
        Courier doubleCourier = courierManager.createCourierData();
        Response createResponse1 = courierManager.createCourier(doubleCourier);

        // Проверка успешного создания первого курьера
        createResponse1.then().statusCode(201);
        String responseBody1 = createResponse1.getBody().asString();
        assertEquals("{\"ok\":true}", responseBody1);

        // Попытка создать второго курьера с тем же логином
        Response createResponse2 = courierManager.createCourier(doubleCourier);

        // Проверка, что второй запрос возвращает ошибку
        createResponse2.then().statusCode(409);
        String responseBody2 = createResponse2.getBody().asString();
        assertEquals("{\"code\":409,\"message\":\"Этот логин уже используется. Попробуйте другой.\"}", responseBody2);

        int courierId = courierManager.loginCourierAndExtractId(doubleCourier);
        courierManager.deleteCourierById(courierId);
    }
    @Test
    @DisplayName("Невозможно создать курьера без логина")
    public void createCourierWithoutLogin() {
        //Генерация данных и установка логина на null
        Courier emptyLoginCourier = courierManager.createCourierData();
        emptyLoginCourier.setLogin(null);
        //Попытка создания курьера где login = null
        Response response = courierManager.createCourier(emptyLoginCourier);
        response.then().statusCode(400);
        String responseBody = response.getBody().asString();
        assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}",responseBody);

    }
    @Test
    @DisplayName("Невозможно создать курьера без пароля")
    public void createCourierWithoutPassword() {
        //Генерация данных и установка пароля на null
        Courier emptyPasswordCourier = courierManager.createCourierData();
        emptyPasswordCourier.setPassword(null);
        //Попытка создания курьера где password = null
        courierManager.createCourier(emptyPasswordCourier);
        Response response = courierManager.createCourier(emptyPasswordCourier);
        response.then().statusCode(400);
        String responseBody = response.getBody().asString();
        assertEquals("{\"code\":400,\"message\":\"Недостаточно данных для создания учетной записи\"}",responseBody);
    }
    @Test
    @DisplayName("Возможно создать курьера без имени")
    public void createCourierWithoutFirstname() {
        // Генерация данных и создание курьера
        Courier emptryFirstnameCourier = courierManager.createCourierData();
        emptryFirstnameCourier.setFirstName(null);
        Response response = courierManager.createCourier(emptryFirstnameCourier);
        //Проверка статус кода - 201, сравнение с полученным ответом
        response.then().statusCode(201);
        String responseBody = response.getBody().asString();
        assertEquals("{\"ok\":true}", responseBody);
        // Удаление созданного курьера
        int courierId = courierManager.loginCourierAndExtractId(emptryFirstnameCourier);
        courierManager.deleteCourierById(courierId);

    }
}
