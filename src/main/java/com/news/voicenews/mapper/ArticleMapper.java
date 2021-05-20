package com.news.voicenews.mapper;

import com.news.voicenews.dto.res.ArticleRes;
import com.news.voicenews.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleRes toArticleRes(Article article);

    List<ArticleRes> toListArticleRes(List<Article> articles);
}
