package com.eltropy.bank.mongo.dao;

import com.eltropy.bank.mongo.entity.AbstractMongoEntity;
import com.mongodb.client.result.DeleteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

public abstract class AbstractMongoDAO {

    public static final String ORDER_DESC = "desc";
    public static final String ORDER_ASC = "asc";
    public static final String QUERY_AND_OPERATOR = "&&";

    public final long NO_START = 0;
    public final static long NO_LIMIT = 0;
    public static final long UNINITIALIZED = -1L;

    public static final int DEFAULT_FETCH_SIZE = 20;
    public static final int MAX_ALLOWED_FETCH_SIZE = 1000;

    public AbstractMongoDAO() {
        super();
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract MongoOperations getMongoOperations();

    protected <E extends AbstractMongoEntity> void saveEntity(E p, String callingUserId) {
        p.setEntityDefaultProperties(callingUserId);
        getMongoOperations().save(p, p.getClass().getSimpleName());
    }

    protected <E extends AbstractMongoEntity> void saveEntity(E p) {
        saveEntity(p, null);
    }

    protected <E extends AbstractMongoEntity> void insertAllEntities(Collection<E> entities, String collectionName, String callingUserId) {
        setEntityDefaultProperties(entities, callingUserId);
        getMongoOperations().insert(entities, collectionName);
    }

    protected <E extends AbstractMongoEntity> void insertAllEntities(Collection<E> entities, String collectionName) {
        insertAllEntities(entities, collectionName, null);
    }

    protected <E extends AbstractMongoEntity> void upsertEntity(E entity, Query q, Update u) {
        upsertEntity(entity, q, u, null);
    }

    protected <E extends AbstractMongoEntity> void upsertEntity(E entity, Query q, Update u, String callingUserId) {
        entity.setEntityDefaultProperties(callingUserId);
        getMongoOperations().upsert(q, u, entity.getClass().getSimpleName());
    }

    protected <T extends AbstractMongoEntity> void updateFirst(Query q, Update u, Class<T> clazz) {
        if (u != null) {
            u.set(AbstractMongoEntity.Constants.LAST_UPDATED, System.currentTimeMillis());
        }
        getMongoOperations().updateFirst(q, u, clazz.getSimpleName());
    }

    protected <T extends AbstractMongoEntity> void updateMulti(Query q, Update u, Class<T> clazz) {
        if (u != null) {
            u.set(AbstractMongoEntity.Constants.LAST_UPDATED, System.currentTimeMillis());
        }
        getMongoOperations().updateMulti(q, u, clazz.getSimpleName());
    }

    protected <T extends AbstractMongoEntity> T findAndModifyEntity(Query q, Update u, FindAndModifyOptions o, Class<T> clazz) {
        if (u != null) {
            u.set(AbstractMongoEntity.Constants.LAST_UPDATED, System.currentTimeMillis());
        }
        return getMongoOperations().findAndModify(q, u, o, clazz, clazz.getSimpleName());
    }

    public <T extends AbstractMongoEntity> T getEntityById(String id, Class<T> clazz) {
        Query query = new Query(Criteria.where("_id").is(id));
        return getMongoOperations().findOne(query, clazz, clazz.getSimpleName());
    }

    public <T extends AbstractMongoEntity> T getEntityById(Long id, Class<T> clazz) {
        Query query = new Query(Criteria.where("_id").is(id));
        return getMongoOperations().findOne(query, clazz, clazz.getSimpleName());
    }

    public <T extends AbstractMongoEntity> T findOne(Query query, Class<T> clazz) {
        T result = null;
        try {
            result = getMongoOperations().findOne(query, clazz, clazz.getSimpleName());
        }
        catch (Exception ex) {
            logger.info("error saving audit  " + ex.getMessage());
        }
        return result;
    }

    public <T extends AbstractMongoEntity> List<T> runQuery(Query query, Class<T> clazz) {
        if (query != null && query.getLimit() > 1000) {
            query.limit(MAX_ALLOWED_FETCH_SIZE);
        }
        return getMongoOperations().find(query, clazz, clazz.getSimpleName());
    }

    public <T extends AbstractMongoEntity> List<T> runQuery(Query query, Class<T> clazz, String collectionName) {
        if (query != null && query.getLimit() > 1000) {
            query.limit(MAX_ALLOWED_FETCH_SIZE);
        }        
        return getMongoOperations().find(query, clazz, collectionName);
    }

    public <T extends AbstractMongoEntity> long queryCount(Query query, Class<T> clazz) {
        return getMongoOperations().count(query, clazz, clazz.getSimpleName());
    }

    public <T extends AbstractMongoEntity> long deleteEntityById(String id, Class<T> clazz) {
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult result = getMongoOperations().remove(query, clazz,
                clazz.getSimpleName());
        return result.getDeletedCount();
    }

    public <T extends AbstractMongoEntity> long deleteEntityById(Long id, Class<T> clazz) {
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult result = getMongoOperations().remove(query, clazz,
                clazz.getSimpleName());
        return result.getDeletedCount();
    }

    // aggregation queries
    public <T extends AbstractMongoEntity> List<T> getEntitiesByUpdationTimeAndLimit(Long fromTime, Long tillTime, Integer limit, Class<T> clazz) {
        Query query = new Query();
        if ((fromTime != null && fromTime > 0)
                || (tillTime != null && tillTime > 0)) {

            Criteria criteria = null;
            if (fromTime != null && fromTime > 0) {
                criteria = Criteria.where(AbstractMongoEntity.Constants.LAST_UPDATED).gte(fromTime);
            }

            if (tillTime != null && tillTime > 0) {
                if (criteria != null) {
                    criteria = criteria.andOperator(Criteria.where(AbstractMongoEntity.Constants.LAST_UPDATED).lt(tillTime));
                } else {
                    criteria = Criteria.where(AbstractMongoEntity.Constants.LAST_UPDATED).lt(tillTime);
                }
            }
            query.addCriteria(criteria);
        }

        if (limit != null) {
            query.limit(limit);
        }
        return runQuery(query, clazz);
    }

    public <E extends AbstractMongoEntity> void setEntityDefaultProperties(Collection<E> entities, String callingUserId) {
        if (entities != null && !entities.isEmpty()) {
            for (E entity : entities) {
                entity.setEntityDefaultProperties(callingUserId);
            }
        }
    }

    public void setFetchParameters(Query query, Integer start, Integer limit) {
        int fetchStart = 0;
        if (start != null && start >= 0) {
            fetchStart = start;
        }
        int fetchSize = DEFAULT_FETCH_SIZE;
        if (limit != null && limit >= 0) {
            fetchSize = limit;
        }
        query.skip(fetchStart);
        query.limit(fetchSize);
    }
}
