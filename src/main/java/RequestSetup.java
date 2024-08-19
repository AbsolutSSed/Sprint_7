import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSetup {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType("application/json")
                .build();
    }
}
