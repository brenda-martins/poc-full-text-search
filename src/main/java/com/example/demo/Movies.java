package com.example.demo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Movies {

    @Id
    private String _id;
    private String plot;
    private int runtime;
    private String title;
    private String fullplot;
    private String rated;
    private int year;
    private String type;
}
