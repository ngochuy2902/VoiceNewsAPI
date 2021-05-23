package com.news.voicenews.respository;

import com.news.voicenews.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository
        extends JpaRepository<Category, String> {

    List<Category> findAll();

    List<Category> findAllByIdIn(List<Long> categoryIds);

    @Query("SELECT c FROM Category c"
            + " JOIN UserCategory uc ON uc.categoryId = c.id"
            + " WHERE uc.userId = :userId")
    List<Category> findAllByUserId(@Param("userId") Long userId);

    Optional<Category> findByName(String categoryName);
}
