package com.news.voicenews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "scores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Score
        implements Serializable {
    private Long id;

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "article_id")

    private String articleId;
    private String url;
    private String category;
    private String domain;
    private Double score;

    @Column(name = "audio_path")
    private String audioPath;
}