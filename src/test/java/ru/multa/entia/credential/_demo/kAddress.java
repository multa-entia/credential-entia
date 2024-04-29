package ru.multa.entia.credential._demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.multa.entia.fakers.impl.Faker;

@Data
@AllArgsConstructor
public class kAddress {
    private String country;
    private String city;
    private String street;
    private Long homeNumber;
    private Long flatNumber;

    public static kAddress random() {
        return new kAddress(
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.str_().random(),
                Faker.long_().random(),
                Faker.long_().random()
        );
    }
}
