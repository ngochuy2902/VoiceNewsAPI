package com.news.voicenews.api.user;

import com.news.voicenews.bloc.ArticleBloc;
import com.news.voicenews.bloc.UserBloc;
import com.news.voicenews.helper.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserBloc userBloc;
    private final ArticleBloc articleBloc;

    public UserController(final UserBloc userBloc, final ArticleBloc articleBloc) {
        this.userBloc = userBloc;
        this.articleBloc = articleBloc;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile() {
        Long currentUserId = SecurityHelper.getUserId();

        return ResponseEntity.ok(userBloc.getUserProfileByUserId(currentUserId));
    }

    @GetMapping("/article")
    public ResponseEntity<?> fetchArticlesByCurrentUser() {
        return ResponseEntity.ok(articleBloc.fetchArticleByCurrentUser());
    }
}
