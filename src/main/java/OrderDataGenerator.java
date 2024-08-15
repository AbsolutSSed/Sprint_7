import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrderDataGenerator {
    Faker faker = new Faker();
    Random random = new Random();
    @Step("Генерация данных заказа")
    public Order generateOrderData(){
        Date futureDate = faker.date().future(30, TimeUnit.DAYS);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedFutureDate = dateFormat.format(futureDate);
        int randomRent = random.nextInt(15) + 1;

        Order order = new Order(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().city(),
                faker.lordOfTheRings().location(),
                faker.phoneNumber().phoneNumber(),
                randomRent,
                formattedFutureDate,
                faker.harryPotter().character(),
                null
                );

        return order;
    }
}
