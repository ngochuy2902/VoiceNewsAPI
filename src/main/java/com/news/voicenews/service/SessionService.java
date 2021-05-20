package com.news.voicenews.service;

import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.Session;
import com.news.voicenews.respository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(final SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Transactional(readOnly = true)
    public List<Session> fetchAllSessions() {
        log.info("Fetch all sessions");
        return sessionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Long findValidSessionId() {
        log.info("Find valid session id");

        return sessionRepository.findValidSessionId()
                                .orElseThrow(() -> new ObjectNotFoundException("session"));
    }
}
