package com.news.voicenews.service;

import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.Score;
import com.news.voicenews.respository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(final ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Transactional(readOnly = true)
    public List<Score> fetchScoresBySessionIdCategoryWithLimit(Long sessionId, String category, Integer limit) {
        log.info("Fetch scores by sessionId #{}, category #{}, limit #{}", sessionId, category, limit);

        return scoreRepository.findScoresBySessionIdAndCategoryWithLimit(sessionId, category, limit);
    }

    @Transactional(readOnly = true)
    public Score findById(Long id) {
        log.info("Find score by id #{}", id);

        return scoreRepository.findById(id)
                              .orElseThrow(() -> new ObjectNotFoundException("article"));
    }
}
