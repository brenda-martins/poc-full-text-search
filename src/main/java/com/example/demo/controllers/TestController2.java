package com.example.demo.controllers;


import com.example.demo.documents.Movies;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test-2")
@Log4j2
@RequiredArgsConstructor
public class TestController2 {

    private final MongoTemplate mongoTemplate;
    private final MongoOperations mongoOps;


    //        this.mongoOps = new MongoTemplate(mongoClient, "sample_mflix");

    @GetMapping
    public List<Movies> list(@RequestParam String word) {
        TextCriteria criteria = TextCriteria
                .forDefaultLanguage()
                .matchingAny(word);

        Query query = TextQuery
                .queryText(criteria)
                .sortByScore();

        return mongoTemplate.find(query, Movies.class);
    }

    @GetMapping(path = "mongo")
    public void testMongoOperations() {
        log.info(this.mongoOps
                .findOne(new Query(Criteria
                        .where("title").is("Wild and Woolly")), Movies.class));
    }
}
