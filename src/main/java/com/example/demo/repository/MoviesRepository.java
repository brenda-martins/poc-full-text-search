package com.example.demo.repository;

import com.example.demo.documents.Movies;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface MoviesRepository extends MongoRepository<Movies, String> {


    @Aggregation(pipeline = {"{ $search: { index: 'movies-autocomplete', autocomplete: { query: ?0, path: 'title' } } }",
            "{ $match: { runtime: 12 } }",
            "{ $limit: 20 }",
            "{ $project: { _id: 1, title: 1  } }"
    })
    List<Movies> listMoviesWithAutoComplete(String word);

}
