package org.example.goods.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.goods.exceptions.ApiException;
import org.example.goods.exceptions.AppException;
import org.example.goods.exceptions.TokenNotValidException;
import org.example.goods.models.http.ExceptionResponse;
import org.example.goods.models.http.TokenValidResponse;
import org.example.goods.utils.http.HttpRequest;
import org.example.goods.utils.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.Objects;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Фильте для jwt токена
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";
    private final UserDetailsService userDetailsService;
    @Value("${api.paths.gateway}")
    private String apiGateWayPath;

    @Autowired
    public JwtFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            doFilter(request, response, filterChain);
            return;
        }
        String token = authHeader.substring(BEARER_PREFIX.length() + 1);
        String email = getEmail(token);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (Objects.equals(email, userDetails.getUsername())) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else {
                throw new TokenNotValidException();
            }
        }
        doFilter(request, response, filterChain);
    }

    private String getEmail(String token) {
        try {
            HttpResponse response = HttpRequest.createRequest(apiGateWayPath + "/auth/valid?token=" + token)
                    .setMethod("GET")
                    .sendRequest();
            if (response.getStatusCode() != 200){
                ExceptionResponse exception = new ObjectMapper()
                        .readValue(response.getResponse(), ExceptionResponse.class);
                throw new ApiException(exception.getStatusCode(), exception.getMessage());
            }
            return new ObjectMapper()
                    .readValue(response.getResponse(),
                            TokenValidResponse.class)
                    .getEmail();
        } catch (ProtocolException | JsonProcessingException e){
            throw new AppException(e.getLocalizedMessage());
        }
    }
}
