package com.dev.springsecuritydemo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true); // Logs client info (IP address)
        loggingFilter.setIncludeQueryString(true); // Logs query parameters
        loggingFilter.setIncludeHeaders(true); // Logs HTTP headers
        loggingFilter.setIncludePayload(true); // Logs the request payload (body)
        loggingFilter.setMaxPayloadLength(10000); // Adjust this as needed for large payloads
        loggingFilter.setAfterMessagePrefix("REQUEST DATA : "); // Custom log message prefix
        return loggingFilter;
    }
}
