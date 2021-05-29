package com.news.voicenews.service;

import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.Session;
import com.news.voicenews.respository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static com.news.voicenews.enums.CrawlerStatus.FINISHED;
import static com.news.voicenews.enums.CrawlerStatus.IN_CRAWLING_NEWS_PROGRESS;

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
        return sessionRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Long findValidSessionId() {
        log.info("Find valid session id");

        return sessionRepository.findValidSessionId(FINISHED)
                                .orElseThrow(() -> new ObjectNotFoundException("session"));
    }

    @Transactional
    public Session createNewSession() {
        log.info("Create new session");

        Session session = Session.builder()
                .createdTime(Instant.now())
                .status(IN_CRAWLING_NEWS_PROGRESS)
                .build();

        return sessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public Session findById(final Long sessionId) {
        log.info("Find session by session id #{}", sessionId);

        return sessionRepository.findById(sessionId)
                                .orElseThrow(() -> new ObjectNotFoundException("session"));
    }

    public Session save(final Session session) {
        log.info("Save session #{}", session);

        return sessionRepository.save(session);
    }
}
