package com.userSegmant.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.userSegmant.starter.models.Segmant;
import com.userSegmant.starter.models.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
@Service
public class UserDAO {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<User> userCollection;

    @PostConstruct
    void init() {
        userCollection = client.getDatabase("test").getCollection("user", User.class);
    }


    public User save(User user) {
        if(user.getId() != null) {

            userCollection.findOneAndReplace(eq("_id", user.getId()), user);
        }else {
            user.setId(new ObjectId());
            userCollection.insertOne(user);
        }
        return user;
    }


    public User findById(String id) {
        return userCollection.find(eq("_id",new ObjectId( id))).first();
    }
}
