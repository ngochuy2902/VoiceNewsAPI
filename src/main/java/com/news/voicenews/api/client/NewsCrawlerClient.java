package com.news.voicenews.api.client;

import com.news.voicenews.dto.req.NewsCrawlerReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.news.voicenews.constant.CrawlerRanker.NEWS_CRAWLER_URL;

@Slf4j
@Component
public class NewsCrawlerClient {

    private final RestTemplate restTemplate;

    public NewsCrawlerClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> publishSessionToNewsCrawler(NewsCrawlerReq newsCrawlerReq) {
        return restTemplate.postForEntity(NEWS_CRAWLER_URL, newsCrawlerReq, String.class);
    }
}
