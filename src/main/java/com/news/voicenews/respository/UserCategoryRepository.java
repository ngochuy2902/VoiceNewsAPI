package com.news.voicenews.respository;

import com.news.voicenews.model.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    List<UserCategory> findAllByUserId(Long userId);
}
