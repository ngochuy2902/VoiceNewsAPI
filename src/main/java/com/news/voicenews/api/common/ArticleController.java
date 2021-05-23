package com.news.voicenews.api.common;

import com.news.voicenews.bloc.ArticleBloc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class ArticleController {

    private final ArticleBloc articleBloc;

    public ArticleController(final ArticleBloc articleBloc) {
        this.articleBloc = articleBloc;
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<?> fetchArticleDetail(@PathVariable Long id) {
        return ResponseEntity.ok(articleBloc.findArticleById(id));
    }

    @GetMapping("/article")
    public ResponseEntity<?> fetchArticlesRanked() {
        return ResponseEntity.ok(articleBloc.fetchArticlesNoLogin());
    }

    @GetMapping("article/name/{categoryName}")
    public ResponseEntity<?> fetchArticlesByCategoryName(@PathVariable final String categoryName) {
        return ResponseEntity.ok(articleBloc.fetchArticlesByCategoryName(categoryName));
    }
}
