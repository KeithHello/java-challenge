package jp.co.axa.apidemo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-API-KEY";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader(HEADER_NAME);

        // Perform the authentication using the API key
        // Note: You should implement your own logic to validate the API key
        if (validateApiKey(apiKey)) {
            Authentication auth = new ApiKeyAuthentication(apiKey);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateApiKey(String apiKey) {
        // Implement any validation logic here
        // For simplicity, I just skip the validation of the API key
        // For production, we may consider to check the API key against a database table
        return StringUtils.isEmpty(apiKey) ? false : true;
    }
}

