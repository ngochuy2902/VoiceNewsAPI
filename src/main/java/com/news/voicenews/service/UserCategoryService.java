package com.news.voicenews.service;

import com.news.voicenews.model.UserCategory;
import com.news.voicenews.model.UserRole;
import com.news.voicenews.respository.UserCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryService(final UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    @Transactional
    public void saveNewUserCategories(Long userId, List<Long> categoryIds) {
        log.info("Save for userId #{} and list roleIds #{}", userId, categoryIds);
        List<UserCategory> userCategories = categoryIds.stream()
                                                       .map(categoryId -> UserCategory.builder()
                                                                                      .userId(userId)
                                                                                      .categoryId(categoryId)
                                                                                      .build())
                                                       .collect(Collectors.toList());
        userCategoryRepository.saveAll(userCategories);
    }

    public List<UserCategory> findAllByUserId(Long userId) {
        log.info("Find user category by user id #{}", userId);

        return userCategoryRepository.findAllByUserId(userId);
    }
}
