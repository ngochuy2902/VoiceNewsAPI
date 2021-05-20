package com.news.voicenews.api.common;


import com.news.voicenews.bloc.CategoryBloc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CategoryController {

    private final CategoryBloc categoryBloc;

    public CategoryController(final CategoryBloc categoryBloc) {
        this.categoryBloc = categoryBloc;
    }

    @GetMapping("/category")
    public ResponseEntity<?> fetchAllCategories() {
        return ResponseEntity.ok(categoryBloc.fetchAllCategories());
    }
}
