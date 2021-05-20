package com.news.voicenews.respository;

import com.news.voicenews.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleMongoRepository
        extends MongoRepository<Article, String> {

    Optional<Article> findArticleByUuid(String id);
}
