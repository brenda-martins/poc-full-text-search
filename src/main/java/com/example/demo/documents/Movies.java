package com.example.demo.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;


@Data
@Document
public class Movies {

    @Id
    private String _id;
    private String plot;
    //    @TextIndexed(weight = 2)
    private String title;
    //    @TextScore
    Float score;
}
