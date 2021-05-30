package com.news.voicenews.api.admin;

import com.news.voicenews.bloc.UserBloc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class UserManagementController {

    private final UserBloc userBloc;

    public UserManagementController(final UserBloc userBloc) {
        this.userBloc = userBloc;
    }


    @GetMapping("/users")
    public ResponseEntity<?> fetchAllUsers() {
        return ResponseEntity.ok(userBloc.fetchAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable final Long id) {
        return ResponseEntity.ok(userBloc.getUserProfileByUserId(id));
    }
}
