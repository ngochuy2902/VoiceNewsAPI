package com.news.voicenews.error.handler;

import com.news.voicenews.dto.res.ErrorMessageRes;
import com.news.voicenews.error.exception.InvalidJwtToken;
import com.news.voicenews.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerFilter
        extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws IOException {
        response.setContentType("application/json");
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidJwtToken ex) {
            responseError(ex.getMessage(),
                          ex.getFieldName(),
                          response,
                          HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            responseError("Server error",
                          "server",
                          response,
                          HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long endTime = System.currentTimeMillis();

        log.info("Time to handle request with url #{} is in #{}", request.getRequestURI(), endTime - startTime);
    }

    private void responseError(final String message,
                               final String fieldName,
                               HttpServletResponse response,
                               HttpStatus httpStatus)
            throws IOException {

        ErrorMessageRes errorMessageRes = ErrorMessageRes.builder()
                                                         .message(message)
                                                         .fieldName(fieldName)
                                                         .build();
        response.setStatus(httpStatus.value());
        response.getWriter().write(JsonHelper.convertObjectToString(errorMessageRes));
    }
}
