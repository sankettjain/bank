package com.eltropy.bank.mongo.beans;


import com.eltropy.bank.enums.EntityState;

public class AbstractMongoEntityBean {

    private Long creationTime;
    private String createdBy;
    private Long lastUpdated;
    private String lastUpdatedBy; // For tracking last updated by
    private EntityState entityState = EntityState.ACTIVE;

    public AbstractMongoEntityBean(Long creationTime, String createdBy,
                                   Long lastUpdated, String lastUpdatedBy) {
        this(creationTime, createdBy, lastUpdated, lastUpdatedBy, null);
    }

    public AbstractMongoEntityBean(Long creationTime, String createdBy,
                                   Long lastUpdated, String lastUpdatedBy, EntityState entityState) {
        this.creationTime = creationTime;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AbstractMongoEntityBean() {
        super();
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    public static class Constants {

        public static final String ID = "id";
        public static final String CREATION_TIME = "creationTime";
        public static final String LAST_UPDATED = "lastUpdated";
        public static final String LAST_UPDATED_BY = "lastUpdatedBy";
        public static final String CREATED_BY = "createdBy";
        public static final String USER_ID = "userId";
        public static final String ENTITY_STATE = "entityState";
    }

}
