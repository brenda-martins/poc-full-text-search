package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoviesRepository  extends MongoRepository<Movies, String> {
}
