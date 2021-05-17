package com.news.voicenews.respository;

import com.news.voicenews.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT MAX(s.id)"
            + " FROM Session s")
    Optional<Session> findCurrentSessionId();

    @Query("SELECT MAX(s.id)"
            + " FROM Session s"
            + " WHERE s.completed = 1")
    Optional<Session> findValidSessionId();

    @Query("UPDATE Session s"
            + " SET s.finishedTime = :finishedTime,"
            + " s.completed = 1"
            + " WHERE s.id = :sessionId")
    void updateSessionCompleted(@Param("sessionId") Long sessionId,
                                @Param("finishedTime") Instant finishedTime);

    List<Session> findAll();
}
