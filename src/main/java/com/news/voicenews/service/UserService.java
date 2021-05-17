package com.news.voicenews.service;

import com.news.voicenews.error.exception.ValidatorException;
import com.news.voicenews.model.User;
import com.news.voicenews.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.ObjectUtils;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        log.info("Get user by username #{}", username);
        return userRepository.findByUsername(username)
                             .orElseThrow(()->new ObjectNotFoundException(username, "user"));
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        log.info("Exists by username #{}", username);
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public User getByUserId(Long userId) {
        log.info("Get user by user id #{}", userId);
        return userRepository.findById(userId)
                             .orElseThrow(()->new ObjectNotFoundException(userId, "user"));
    }

    @Transactional
    public void saveNew(final User user) {
        log.info("Save new user with info #{}", user);
        validateAccountBeforeSaveNew(user);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    private void validateAccountBeforeSaveNew(final User user) {
        if(ObjectUtils.isNotEmpty(user.getId()))
            throw new ValidatorException("Invalid user id", user.getId().toString());
    }
}
