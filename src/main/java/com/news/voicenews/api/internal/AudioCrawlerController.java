package com.news.voicenews.api.internal;

import com.news.voicenews.bloc.CrawlerBloc;
import com.news.voicenews.dto.req.FromAudioCrawlerReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/internal/crawler")
public class AudioCrawlerController {

    private final CrawlerBloc crawlerBloc;

    public AudioCrawlerController(final CrawlerBloc crawlerBloc) {
        this.crawlerBloc = crawlerBloc;
    }

    @PostMapping("/audio")
    public ResponseEntity<?> updateNewsCrawlerStatus(@RequestBody @Valid final FromAudioCrawlerReq fromAudioCrawlerReq) {
        return new ResponseEntity<>(crawlerBloc.updateAudioPathFromAudioCrawler(fromAudioCrawlerReq), OK);
    }
}
