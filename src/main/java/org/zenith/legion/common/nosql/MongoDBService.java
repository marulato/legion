package org.zenith.legion.common.nosql;

import org.zenith.legion.common.base.BaseDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService <T extends BaseDocument> {

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(MongoTemplate.class);

    public void save(T document) {
        if (document != null && document.getClass().isAnnotationPresent(Document.class)) {
            log.debug("Saving document to MongoDB");
            mongoTemplate.save(document);
            log.debug("Document Saved Successfully");
        } else {
            log.error("Unsupported Document");
        }
    }

    public void update(T document) {
        if (document != null &&
                mongoTemplate.findById(document.getId(), document.getClass()) != null) {
            save(document);
        }
    }

    public void delete(T document) {
        mongoTemplate.remove(document);
    }

    public T getById(long id, Class<T> type) {
        return mongoTemplate.findById(id, type);
    }
}
