package com.eltropy.bank.managers;

import com.eltropy.bank.mongo.entity.Account;
import com.eltropy.bank.pojos.LinkAccountToCustomerReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BankManager {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void linkAccountToCustomer(LinkAccountToCustomerReq linkAccountToCustomerReq) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Account.Constants.ID).is(linkAccountToCustomerReq.getAccountId()));
        Account account = mongoTemplate.findOne(query, Account.class);
        if (Objects.nonNull(account)) {
            account.setCustomerId(linkAccountToCustomerReq.getCustomerId());
            mongoTemplate.save(account);
            logger.info("Account {} linked with {} customer successfully", linkAccountToCustomerReq.getAccountId(), linkAccountToCustomerReq.getCustomerId());
        }
    }
}
