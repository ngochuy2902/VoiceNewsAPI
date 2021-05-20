package com.news.voicenews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Session
        implements Serializable {

    @Id
    private Long id;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "finished_time")
    private Instant finishedTime;

    @Column(name = "completed")
    private Boolean completed;
}
