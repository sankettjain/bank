package com.eltropy.bank.mongo.entity;

import com.eltropy.bank.enums.Role;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User extends AbstractMongoStringIdEntity{

    @Indexed(background = true, unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String password;
    private Role role;
    private String city;
    private String country;

    public static class Constants {

        public static final String EMAIL = "email";
    }
}
