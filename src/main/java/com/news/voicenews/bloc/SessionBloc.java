package com.news.voicenews.bloc;

import com.news.voicenews.model.Session;
import com.news.voicenews.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SessionBloc {

    private SessionService sessionService;

    public SessionBloc(final SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public List<Session> fetchAllSessions() {
        log.info("Fetch all sessions");
        return sessionService.fetchAllSessions();
    }
}
