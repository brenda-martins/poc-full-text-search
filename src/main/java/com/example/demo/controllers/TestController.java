package com.example.demo.controllers;

import com.example.demo.repository.MoviesRepository;
import com.example.demo.documents.Movies;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;


@RestController
@RequestMapping("/test")
@Log4j2
public class TestController {

    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> mongoCollection;
    private final MoviesRepository moviesRepository;


    public TestController(final MongoClient mongoClient, final MoviesRepository moviesRepository) {
        this.mongoDatabase = mongoClient.getDatabase("sample_mflix");
        this.mongoCollection = mongoDatabase.getCollection("movies");
        this.moviesRepository = moviesRepository;
    }

    @GetMapping(path = "list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Document>> listDefaultIndex(@RequestParam String word) {
        return new ResponseEntity<>(fullTextSearch(word), HttpStatus.OK);
    }

    @GetMapping(path = "list-autocomplete")
    public List<Movies> listMoviesWithAutoComplete(@RequestParam String word) {
        return moviesRepository.listMoviesWithAutoComplete(word);
    }

    List<Document> fullTextSearch(String word) {
        return mongoCollection.aggregate(listBson(word)).into(new ArrayList<Document>());
    }

    public List<Document> listBson(String search) {
        return Arrays.asList(new Document("$search",
                        new Document("text",
                                new Document("query", search)
                                        .append("path", Arrays.asList("title", "plot"))
                                        .append("fuzzy",
                                                new Document("maxEdits", 2L)))),
                new Document("$project",
                        new Document("title", 1L)
                                .append("plot", 1L)));
    }


}
