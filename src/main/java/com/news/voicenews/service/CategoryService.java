package com.news.voicenews.service;

import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.Category;
import com.news.voicenews.respository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> fetchAllCategories() {
        log.info("Fetch all categories");
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Category> findAllByIds(List<Long> categoryIds) {
        log.info("Fetch by category ids #{}", categoryIds);

        return categoryRepository.findAllByIdIn(categoryIds);
    }

    @Transactional(readOnly = true)
    public List<Category> findAllByUserId(final Long userId) {
        log.info("Find all categories by user id #{}", userId);

        return categoryRepository.findAllByUserId(userId);
    }

    public Category findByName(final String categoryName) {
        log.info("Find by category name #{}", categoryName);

        return categoryRepository.findByName(categoryName)
                                 .orElseThrow(() -> new ObjectNotFoundException("category"));
    }
}
