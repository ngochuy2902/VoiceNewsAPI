package com.news.voicenews.service;

import com.news.voicenews.model.UserRole;
import com.news.voicenews.respository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(final UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public void deleteOldAndSaveNew(final Long userId, List<Long> roleIds) {
        log.info("Delete old accountRole then save for accountId #{} and list roleIds #{}", userId, roleIds);
        List<UserRole> oldAccountRoles = userRoleRepository.findByUserId(userId);
        userRoleRepository.deleteAll(oldAccountRoles);

        saveNew(userId, roleIds);
    }

    @Transactional
    public void saveNew(final Long userId, List<Long> roleIds) {
        log.info("Save for userId #{} and list roleIds #{}", userId, roleIds);
        List<UserRole> userRoles = roleIds.stream()
                                                .map(roleId -> UserRole.builder()
                                                                          .userId(userId)
                                                                          .roleId(roleId).build())
                                                .collect(Collectors.toList());
        userRoleRepository.saveAll(userRoles);
    }
}

