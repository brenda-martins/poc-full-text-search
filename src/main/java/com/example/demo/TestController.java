package com.example.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;

import javax.print.Doc;

@RestController
@RequestMapping("/test")
//@RequiredArgsConstructor
@Log4j2
public class TestController {

    //    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private final MongoCollection<Document> mongoCollection;

//    private final MongoTemplate mongoTemplate;

    public TestController(final MongoClient mongoClient) {
        this.mongoDatabase = mongoClient.getDatabase("sample_mflix");

        this.mongoCollection = mongoDatabase.getCollection("movies");
    }

    List<Document> fullTextSearch(String searchWord) {
        return mongoCollection.aggregate(listBson(searchWord)).into(new ArrayList<Document>());

//        TextCriteria criteria = TextCriteria
//                .forDefaultLanguage()
//                .matchingAny(searchWord);
//
//        TextQuery query = TextQuery.queryText(criteria).sortByScore();
//        log.info("TESTE ", test());
//        System.out.println(test());
//        return mongoTemplate.find(query, Movies.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Document>> test(@RequestParam String searchWord) {
        return new ResponseEntity<>(fullTextSearch(searchWord), HttpStatus.OK);
    }

    public List<Document> listBson(String search) {
        return Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", search)
                                        .append("path", Arrays.asList("title", "plot"))
                                        .append("fuzzy",
                                                new Document("maxEdits", 1L)))),
                new Document("$project",
                        new Document("title", 1L)
                                .append("plot", 1L)
                                .append("fullplot", 1L)));
    }


}
