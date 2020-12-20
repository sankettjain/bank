package com.eltropy.bank.mongo.beans;


public class AbstractMongoStringIdEntityBean extends AbstractMongoEntityBean {

    private String id;

    public AbstractMongoStringIdEntityBean() {
    }

    public AbstractMongoStringIdEntityBean(String id, Long creationTime, String createdBy, Long lastUpdated, String callingUserId) {
        super(creationTime, createdBy, lastUpdated, callingUserId);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
