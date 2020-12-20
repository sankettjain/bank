package com.eltropy.bank.controller;

import com.eltropy.bank.enums.Role;
import com.eltropy.bank.managers.UserManager;
import com.eltropy.bank.mongo.entity.User;
import com.eltropy.bank.pojos.UserReqRes;
import com.eltropy.bank.util.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loginFlow")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public UserReqRes login(@RequestParam("user") String userEmailId, @RequestParam("password") String pwd) throws CustomException {

        User user = userManager.getUserByEmailId(userEmailId);
        if (Objects.isNull(user)) {
            throw new CustomException("BAD_REQUEST", "User not present", null);
        }
        if (!StringUtils.equals(user.getPassword(), pwd)) {
            throw new CustomException("BAD_REQUEST", "User not Valid", null);
        }
        String token = getJWTToken(userEmailId,user.getRole());
        UserReqRes userReqRes = new UserReqRes();
        userReqRes.setUserName(user.getFullName());
        userReqRes.setToken(token);
        return userReqRes;
    }

    private String getJWTToken(String username, Role role) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role.name());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}
