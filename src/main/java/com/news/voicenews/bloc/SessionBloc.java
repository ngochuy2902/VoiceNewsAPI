package com.news.voicenews.bloc;

import com.news.voicenews.dto.res.SessionRes;
import com.news.voicenews.mapper.SessionMapper;
import com.news.voicenews.model.Session;
import com.news.voicenews.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SessionBloc {

    private SessionService sessionService;

    public SessionBloc(final SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public List<SessionRes> fetchAllSessions() {
        log.info("Fetch all sessions");
        List<Session> sessions = sessionService.fetchAllSessions();

        return sessions.stream()
                       .map(SessionMapper.INSTANCE::toSessionRes)
                       .collect(Collectors.toList());
    }
}
