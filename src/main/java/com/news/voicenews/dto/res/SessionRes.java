package com.news.voicenews.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.news.voicenews.enums.CrawlerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionRes {
    private Long id;

    @JsonProperty("created_time")
    private Instant createdTime;

    @Enumerated(EnumType.STRING)
    private CrawlerStatus status;

    @JsonProperty("finished_time")
    private Instant finishedTime;
}
