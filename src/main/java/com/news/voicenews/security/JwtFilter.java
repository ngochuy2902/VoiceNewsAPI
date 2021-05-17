package com.news.voicenews.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {

        String anthHeader = request.getHeader("Authorization");
        if (anthHeader != null && anthHeader.startsWith("Bearer" + " ")) {
            String token = anthHeader.replace("Bearer ", "").trim();


        }
    }
}
