package com.eltropy.bank.mongo.entity;

import com.eltropy.bank.enums.AccountType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "account")
public class Account extends AbstractMongoStringIdEntity{

    private String accountName;
    private String customerId;
    private AccountType accountType;
    private Double amount;

    public static class Constants{

        public static final String ID = "id";
    }
}
