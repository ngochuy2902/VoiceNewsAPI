package com.news.voicenews.service;

import com.news.voicenews.enums.RoleType;
import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.Role;
import com.news.voicenews.respository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> fetchRolesByUserId(Long userId) {

        return roleRepository.fetchByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Role getRoleByRoleType(final RoleType roleType) {
        log.info("Get role by type #{}", roleType.name());

        return roleRepository.findByRoleType(roleType)
                             .orElseThrow(() -> new ObjectNotFoundException("role"));
    }

    @Transactional(readOnly = true)
    public List<Role> fetchByUserId(final Long userId) {
        log.info("Fetch roles by user id #{}", userId);

        return roleRepository.fetchByUserId(userId);
    }
}
