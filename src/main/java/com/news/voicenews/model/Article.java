package com.news.voicenews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "articles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Article {

    private ObjectId _id;
    private String uuid;
    private String url;
    private String domain;
    private String title;
    private String category;
    private String category_url;
    private Instant time;
    private String content;
}