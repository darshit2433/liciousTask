package com.userSegmant.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.userSegmant.starter.models.Segmant;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
@Service
public class SegmantDAO {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Segmant> segmantCollection;

    @PostConstruct
    void init() {
        segmantCollection = client.getDatabase("test").getCollection("Segmant", Segmant.class);
    }


    public Segmant save(Segmant segmant) {
        if(segmant.getId() != null) {

            segmantCollection.findOneAndReplace(eq("_id", segmant.getId()), segmant);
        }else {
            segmant.setId(new ObjectId());
            segmantCollection.insertOne(segmant);
        }
        return segmant;
    }


    public List<Segmant> findAll() {
        return segmantCollection.find().into(new ArrayList<>());
    }
}
