import com.github.javafaker.Faker;

public class CourierDataGenerator {
    Faker faker = new Faker();
    public Courier generateCourierData(){
        Courier courier = new Courier(faker.name().username(),faker.harryPotter().character(),faker.name().firstName());
                return courier;
    }

}