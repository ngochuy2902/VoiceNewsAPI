package com.news.voicenews.respository;

import com.news.voicenews.enums.RoleType;
import com.news.voicenews.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r"
            + " JOIN UserRole ur ON ur.roleId = r.id"
            + " WHERE ur.userId = :userId")
    List<Role> fetchByUserId(@Param("userId") Long userId);

    Optional<Role> findByRoleType(RoleType roleType);
}
