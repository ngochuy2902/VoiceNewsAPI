package com.news.voicenews.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.news.voicenews.enums.CrawlerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsCrawlerReq {

    @NotNull
    @JsonProperty("session_id")
    private Long sessionId;

    @Enumerated(EnumType.STRING)
    private CrawlerStatus status;
}
