package com.eltropy.bank.controller;

import com.eltropy.bank.managers.BankManager;
import com.eltropy.bank.mongo.entity.Account;
import com.eltropy.bank.mongo.entity.User;
import com.eltropy.bank.mongo.utils.BankRepository;
import com.eltropy.bank.mongo.utils.UserRepository;
import com.eltropy.bank.pojos.LinkAccountToCustomerReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("bank")
public class BankController {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankManager bankManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority(T(com.eltropy.bank.enums.Role).EMPLOYEE.name())")
    public Account createAccount(@RequestBody Account account) {
        logger.info("Saving user.");
        account.setEntityDefaultProperties("ADMIN");
        return bankRepository.save(account);
    }

    @RequestMapping(value = "/linkAccountToCustomer", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority(T(com.eltropy.bank.enums.Role).EMPLOYEE.name())")
    public void linkAccountToCustomer(@RequestBody LinkAccountToCustomerReq linkAccountToCustomerReq) {
        logger.info("LinkAccountToCustomerReq req: {}", linkAccountToCustomerReq);
        bankManager.linkAccountToCustomer(linkAccountToCustomerReq);
    }

    @RequestMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(T(com.eltropy.bank.enums.Role).EMPLOYEE.name())")
    public void deleteById(@PathVariable("id") String id) {
        try {
            bankRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("" + e);
        }
    }

    @RequestMapping(value = "/getCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority(T(com.eltropy.bank.enums.Role).EMPLOYEE.name())")
    public User getCustomer(@RequestParam(name = "customerId") String customerId) {
        logger.info("customerId req: {}", customerId);
        Optional<User> userData =  userRepository.findById(customerId);
        if (userData.isPresent()) {
            return userData.get();
        } else {
            return null;
        }
    }
}
