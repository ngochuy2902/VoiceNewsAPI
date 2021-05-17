package com.news.voicenews.api;

import com.news.voicenews.bloc.SessionBloc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionBloc sessionBloc;

    public SessionController(final SessionBloc sessionBloc) {
        this.sessionBloc = sessionBloc;
    }

    @GetMapping
    public ResponseEntity<?> fetchAllSessions() {
        return ResponseEntity.ok(sessionBloc.fetchAllSessions());
    }
}
