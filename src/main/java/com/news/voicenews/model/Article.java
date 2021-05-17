package com.news.voicenews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Article
        implements Serializable {
    private String id;
    private String url;
    private String domain;
    private String title;
    private String category;
    private String categoryUrl;
    private Instant time;
    private String content;
}