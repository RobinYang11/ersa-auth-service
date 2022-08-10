package com.ersa.authservice.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.MongoDriverInformation;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.internal.MongoClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Collections;

//@Configuration
public class MongoConfig {

    private String userName ="root";

    private String password = "1hbl#QTmongo";
    private String host = "106.14.237.78";
    private int port = 27017;
    private String database ="ersa";

//    @Bean
    public MongoDatabaseFactory getFactory(){

        MongoCredential credential = MongoCredential.createCredential(userName,password,password.toCharArray());
        ServerAddress serverAddress =new ServerAddress(host,port);
        MongoDriverInformation mongoDriverInformation =MongoDriverInformation.builder().build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                    builder.hosts(Collections.singletonList(serverAddress)))
                .credential(credential).build();

        MongoClient client = new MongoClientImpl(settings,mongoDriverInformation);
        return new SimpleMongoClientDatabaseFactory(client,database);
    }
}
