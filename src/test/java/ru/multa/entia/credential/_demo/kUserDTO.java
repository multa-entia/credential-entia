package ru.multa.entia.credential._demo;

import lombok.Data;
import ru.multa.entia.fakers.impl.Faker;

@Data
public class kUserDTO {
    private Long age;
    private String name;
    private kAddress address;

    public static kUserDTO random() {
        kUserDTO dto = new kUserDTO();
        dto.setAddress(kAddress.random());
        dto.setName(Faker.str_().random());
        dto.setAge(Faker.long_().between(20L, 30L));
        return dto;
    }
}
