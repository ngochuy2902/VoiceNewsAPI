package com.news.voicenews.api.admin;

import com.news.voicenews.bloc.CrawlerBloc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/admin")
public class CrawlerController {

    private final CrawlerBloc crawlerBloc;

    public CrawlerController(final CrawlerBloc crawlerBloc) {
        this.crawlerBloc = crawlerBloc;
    }

    @PostMapping("/crawler")
    public ResponseEntity<?> startCrawler() {
        return new ResponseEntity<>(crawlerBloc.startCrawler(), CREATED);
    }
}
