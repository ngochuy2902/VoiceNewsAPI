package com.news.voicenews.respository;

import com.news.voicenews.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT MAX(s.id)"
            + " FROM Session s"
            + " WHERE s.completed = true")
    Optional<Long> findValidSessionId();

    List<Session> findAll();
}
