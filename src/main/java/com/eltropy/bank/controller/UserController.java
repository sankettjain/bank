package com.eltropy.bank.controller;

import com.eltropy.bank.enums.Role;
import com.eltropy.bank.mongo.entity.User;
import com.eltropy.bank.mongo.utils.UserRepository;
import com.eltropy.bank.pojos.UserReqRes;
import com.eltropy.bank.util.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import com.eltropy.bank.managers.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority(T(com.eltropy.bank.enums.Role).ADMIN.name())")
    public User create(@RequestBody User user) {
        logger.info("Saving user.");
        user.setEntityDefaultProperties("ADMIN");
        return userRepository.save(user);
    }

    @RequestMapping(value = "/createCustomer", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority(T(com.eltropy.bank.enums.Role).ADMIN.name()) " +
    "|| hasAuthority(T(com.eltropy.bank.enums.Role).EMPLOYEE.name())")
    public User createCustomer(@RequestBody User user) {
        logger.info("Saving user.");
        user.setEntityDefaultProperties("ADMIN");
        return userRepository.save(user);
    }

}