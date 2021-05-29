package com.news.voicenews.bloc;

import com.news.voicenews.api.client.AudioCrawlerClient;
import com.news.voicenews.api.client.NewsCrawlerClient;
import com.news.voicenews.api.client.RankerClient;
import com.news.voicenews.dto.req.AudioCrawlerReq;
import com.news.voicenews.dto.req.FromAudioCrawlerReq;
import com.news.voicenews.dto.req.NewsCrawlerReq;
import com.news.voicenews.model.Article;
import com.news.voicenews.model.Category;
import com.news.voicenews.model.Score;
import com.news.voicenews.model.Session;
import com.news.voicenews.service.ArticleService;
import com.news.voicenews.service.CategoryService;
import com.news.voicenews.service.ScoreService;
import com.news.voicenews.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.news.voicenews.constant.CrawlerRanker.BASE_AUDIO_URL;
import static com.news.voicenews.enums.CrawlerStatus.AUDIO_CRAWLING_FAILED;
import static com.news.voicenews.enums.CrawlerStatus.AUDIO_CRAWLING_SUCCESS;
import static com.news.voicenews.enums.CrawlerStatus.FAILED;
import static com.news.voicenews.enums.CrawlerStatus.FINISHED;
import static com.news.voicenews.enums.CrawlerStatus.IN_CRAWLING_AUDIO_PROGRESS;
import static com.news.voicenews.enums.CrawlerStatus.IN_RANKER_PROGRESS;
import static com.news.voicenews.enums.CrawlerStatus.NEWS_CRAWLER_FAILED;
import static com.news.voicenews.enums.CrawlerStatus.NEWS_CRAWLER_SUCCESS;
import static com.news.voicenews.enums.CrawlerStatus.RANKER_FAILED;
import static com.news.voicenews.enums.CrawlerStatus.RANKER_SUCCESS;

@Slf4j
@Service
public class CrawlerBloc {

    private final SessionService sessionService;
    private final CategoryService categoryService;
    private final ScoreService scoreService;
    private final ArticleService articleService;
    private final AudioCrawlerClient audioCrawlerClient;
    private final NewsCrawlerClient newsCrawlerClient;
    private final RankerClient rankerClient;

    public CrawlerBloc(final SessionService sessionService,
                       final CategoryService categoryService,
                       final ScoreService scoreService,
                       final ArticleService articleService,
                       final AudioCrawlerClient audioCrawlerClient,
                       final NewsCrawlerClient newsCrawlerClient,
                       final RankerClient rankerClient) {
        this.sessionService = sessionService;
        this.categoryService = categoryService;
        this.scoreService = scoreService;

        this.articleService = articleService;
        this.audioCrawlerClient = audioCrawlerClient;
        this.newsCrawlerClient = newsCrawlerClient;
        this.rankerClient = rankerClient;
    }

    @Transactional
    public Long startCrawler() {
        log.info("Start crawler");

        Session session = sessionService.createNewSession();
        NewsCrawlerReq newsCrawlerReq = NewsCrawlerReq.builder()
                                                      .sessionId(session.getId())
                                                      .build();
        newsCrawlerClient.publishSessionToNewsCrawler(newsCrawlerReq);
        return session.getId();
    }

    @Transactional
    public Long updateStatusFromNewsCrawler(NewsCrawlerReq newsCrawlerReq) {
        log.info("Update status from new crawler #{}", newsCrawlerReq);

        Session session = sessionService.findById(newsCrawlerReq.getSessionId());

        if (newsCrawlerReq.getStatus() == NEWS_CRAWLER_SUCCESS) {
            session.setStatus(IN_RANKER_PROGRESS);
            rankerClient.publishSessionToRanker(newsCrawlerReq);
        } else if (newsCrawlerReq.getStatus() == NEWS_CRAWLER_FAILED) {
            log.info("News crawling failed with session id #{}", newsCrawlerReq.getSessionId());
            session.setStatus(FAILED);
        }
        sessionService.save(session);
        return newsCrawlerReq.getSessionId();
    }

    @Transactional
    public Long updateStatusFromRanker(NewsCrawlerReq newsCrawlerReq)
            throws InterruptedException {
        log.info("Update status from ranker with news crawler #{}", newsCrawlerReq);

        Session session = sessionService.findById(newsCrawlerReq.getSessionId());

        if (newsCrawlerReq.getStatus() == RANKER_SUCCESS) {
            session.setStatus(IN_CRAWLING_AUDIO_PROGRESS);
            sessionService.save(session);
            publishSessionToAudioCrawler(session);
        } else if (newsCrawlerReq.getStatus() == RANKER_FAILED){
            log.info("Rank failed with session id #{}", newsCrawlerReq.getSessionId());
            session.setStatus(FAILED);
            sessionService.save(session);
        }
        return newsCrawlerReq.getSessionId();
    }

    @Transactional
    public String updateAudioPathFromAudioCrawler(FromAudioCrawlerReq fromAudioCrawlerReq) {
        log.info("Update status and audio path from audio crawler #{}", fromAudioCrawlerReq);

        List<Score> scores = scoreService.findByAllArticleId(fromAudioCrawlerReq.getUuid());

        if (fromAudioCrawlerReq.getStatus() == AUDIO_CRAWLING_SUCCESS) {
            for (Score score : scores) {
                score.setAudioPath(BASE_AUDIO_URL + fromAudioCrawlerReq.getUuid() + ".mp3");
            }
            scoreService.updateAudioPathFromAudioCrawler(scores);
        } else  if (fromAudioCrawlerReq.getStatus() == AUDIO_CRAWLING_FAILED) {
            log.info("Failed to crawl audio with article id #{}", fromAudioCrawlerReq.getUuid());
        }
        return fromAudioCrawlerReq.getUuid();
    }

    @Async
    public void publishSessionToAudioCrawler(Session session)
            throws InterruptedException {
        log.info("Publish session to audio crawler with session #{}", session);

        List<Score> scoresShouldBeCrawlAudio = getScoresShouldBeCrawlAudio(session);

        log.info("Number of audios #{}", scoresShouldBeCrawlAudio.size());
        int i = 1;
        for (Score score : scoresShouldBeCrawlAudio) {
            log.info("Crawling audio #{}", i++);

            Article article = articleService.fetchArticleById(score.getArticleId());

            Category category = categoryService.findByName(score.getCategory());

            String content = normalizeContent(article.getTitle(), article.getContent(), category.getDescription());
            audioCrawlerClient.publishSessionToAudioCrawler(AudioCrawlerReq.builder()
                                                                           .uuid(score.getArticleId())
                                                                           .text(content)
                                                                           .build());
            int delay;
            if (i % 10 != 0) {
                delay = RandomUtils.nextInt(10000, 20000);
            } else {
                delay = RandomUtils.nextInt(30000, 40000);
            }
            log.info("Delay in #{} milliseconds", delay);
            TimeUnit.MILLISECONDS.sleep(delay);
        }
        session.setStatus(FINISHED);
        session.setFinishedTime(Instant.now());
        sessionService.save(session);
        log.info("Session is finished #{}", session);
    }

    public List<Score> getScoresShouldBeCrawlAudio(Session session) {
        log.info("Get scores should be crawl audio");

        List<Category> categories = categoryService.fetchAllCategories();

        List<Score> scoresShouldBeCrawlAudio = new ArrayList<>();
        for (Category category : categories) {
            List<Score> scores = scoreService.fetchScoresBySessionIdCategoryWithLimit(session.getId(),
                                                                                      category.getName(),
                                                                                      15);
            for (Score score : scores) {
                Score oldScore = scoreService.findByArticleIdAndAudioPathNotNull(score.getArticleId());
                if (Objects.nonNull(oldScore)) {
                    scoreService.updateAudioPathByOldScorePath(score, oldScore.getAudioPath());
                    continue;
                }
                scoresShouldBeCrawlAudio.add(score);
            }
        }
        return scoresShouldBeCrawlAudio;
    }

    public String normalizeContent(final String articleTitle,
                                   final String articleContent,
                                   final String articleCategory) {

        String content;

        content = "Tin " + articleCategory + ". ";
        content = content + articleTitle + ". ";
        content = content + articleContent;

        content = content.replace("'", "");
        content = content.replace("\"", "");
        content = content.replace("tp", " thành phố ");
        content = content.replace("TP", " thành phố ");
        content = content.replace("HCM", "hồ chí minh");
        content = content.replace("nCoV", "covid");
        content = content.replace("T.Ư", "trung ương");
        content = content.replace("TW", "trung ương");
        content = content.replace("T.W", "trung ương");

        return content;
    }
}
