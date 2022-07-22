package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Log4j2
public class TestController {

    private final MoviesRepository moviesRepository;

    private final MongoTemplate mongoTemplate;

    List<Movies> fullTextSearch(String searchWord) {
        TextCriteria criteria = TextCriteria
                .forDefaultLanguage()
                .matchingAny(searchWord);

        TextQuery query = TextQuery.queryText(criteria).sortByScore();
        log.info("TESTE ", test());
        System.out.println(test());
        return mongoTemplate.find(query, Movies.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Movies>> test(@RequestParam String searchWord) {
        return new ResponseEntity<>(fullTextSearch(searchWord), HttpStatus.OK);
    }

    public List<Document> test() {
        return Arrays.asList(new Document("$search",
                new Document("text",
                        new Document("query", "figth club")
                                .append("path", Arrays.asList("title", "plot"))
                                .append("fuzzy",
                                        new Document("maxEdits", 1L))))).stream().collect(Collectors.toList());
    }


}
