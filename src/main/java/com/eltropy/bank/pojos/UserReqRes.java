package com.eltropy.bank.pojos;

import lombok.Data;

@Data
public class UserReqRes {
    private String userName;
    private String password;
    private String token;
}
