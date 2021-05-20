package com.news.voicenews.service;

import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.Article;
import com.news.voicenews.respository.ArticleMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ArticleService {
    private final ArticleMongoRepository articleMongoRepository;

    public ArticleService(final ArticleMongoRepository articleMongoRepository) {
        this.articleMongoRepository = articleMongoRepository;
    }

    @Transactional(readOnly = true)
    public Article fetchArticleById(String id) {
        log.info("Fetch article by id #{}", id);
        return articleMongoRepository.findArticleByUuid(id)
                                     .orElseThrow(() -> new ObjectNotFoundException("article"));
    }
}

