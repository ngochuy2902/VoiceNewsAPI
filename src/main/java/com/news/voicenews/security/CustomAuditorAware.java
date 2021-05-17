package com.news.voicenews.security;

import com.news.voicenews.constant.Constant;
import com.news.voicenews.helper.SecurityHelper;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CustomAuditorAware
        implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityHelper.getUsername().orElse(Constant.SYSTEM_ACCOUNT));
    }
}
