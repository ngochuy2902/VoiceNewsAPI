package com.news.voicenews.api.client;

import com.news.voicenews.dto.req.AudioCrawlerReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.news.voicenews.constant.CrawlerRanker.AUDIO_CRAWLER_URL;

@Slf4j
@Component
public class AudioCrawlerClient {

    private final RestTemplate restTemplate;

    public AudioCrawlerClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> publishSessionToAudioCrawler(AudioCrawlerReq audioCrawlerReq) {
        return restTemplate.postForEntity(AUDIO_CRAWLER_URL, audioCrawlerReq, String.class);
    }
}
