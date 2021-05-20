package com.news.voicenews.bloc;


import com.news.voicenews.model.Category;
import com.news.voicenews.respository.CategoryRepository;
import com.news.voicenews.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CategoryBloc {

    private final CategoryService categoryService;

    public CategoryBloc(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public List<Category> fetchAllCategories() {
        log.info("Fetch all categories");
        return categoryService.fetchAllCategories();
    }
}
