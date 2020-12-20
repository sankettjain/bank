package com.eltropy.bank.pojos;

import lombok.Data;

@Data
public class LinkAccountToCustomerReq {

    private String accountId;
    private String customerId;
}
