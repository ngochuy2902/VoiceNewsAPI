package com.news.voicenews.api.internal;

import com.news.voicenews.bloc.CrawlerBloc;
import com.news.voicenews.dto.req.NewsCrawlerReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/internal")
public class RankerController {

    private final CrawlerBloc crawlerBloc;

    public RankerController(final CrawlerBloc crawlerBloc) {
        this.crawlerBloc = crawlerBloc;
    }

    @PostMapping("/ranker")
    public ResponseEntity<?> updateRankerStatus(@RequestBody @Valid final NewsCrawlerReq newsCrawlerReq)
            throws InterruptedException {
        crawlerBloc.updateStatusFromRanker(newsCrawlerReq);
        return ResponseEntity.noContent().build();
    }
}
