package com.news.voicenews.security;

import com.news.voicenews.error.exception.ObjectNotFoundException;
import com.news.voicenews.model.User;
import com.news.voicenews.respository.RoleRepository;
import com.news.voicenews.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(final UserRepository userRepository,
                                    final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::buildSpringSecurityUser)
                .orElseThrow(() -> new ObjectNotFoundException("user"));
    }

    private CustomUserDetails buildSpringSecurityUser(final User user) {
        Set<GrantedAuthority> grantedAuthorities =
                roleRepository.fetchByUserId(user.getId())
                              .stream()
                              .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                              .collect(Collectors.toSet());

        return new CustomUserDetails(user.getId(),
                                     user.getUsername(),
                                     user.getPassword(),
                                     grantedAuthorities);
    }
}
