package com.news.voicenews.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleRes {

    private Long id;

    @JsonProperty("article_id")
    private String articleId;

    private String url;
    private String domain;
    private String title;
    private String category;
    private Instant time;
    private String content;

    @JsonProperty("audio_path")
    private String audioPath;
}
