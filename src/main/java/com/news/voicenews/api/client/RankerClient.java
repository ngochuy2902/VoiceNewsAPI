package com.news.voicenews.api.client;

import com.news.voicenews.dto.req.NewsCrawlerReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.news.voicenews.constant.CrawlerRanker.RANKER_URL;

@Slf4j
@Component
public class RankerClient {

    private final RestTemplate restTemplate;

    public RankerClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> publishSessionToRanker(NewsCrawlerReq newsCrawlerReq) {
        NewsCrawlerReq audioCrawlerReq = NewsCrawlerReq.builder()
                                                       .sessionId(newsCrawlerReq.getSessionId())
                                                       .build();
        return restTemplate.postForEntity(RANKER_URL, audioCrawlerReq, String.class);
    }
}
