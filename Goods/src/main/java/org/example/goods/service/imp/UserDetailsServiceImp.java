package org.example.goods.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.goods.exceptions.ApiException;
import org.example.goods.exceptions.AppException;
import org.example.goods.models.http.ExceptionResponse;
import org.example.goods.models.http.UserResponse;
import org.example.goods.utils.http.HttpRequest;
import org.example.goods.utils.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.net.ProtocolException;
import java.util.Arrays;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Реализация сервиса userDetails
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Value("${api.paths.gateway}")
    private String apiGateWayPath;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            HttpResponse response = HttpRequest.createRequest(apiGateWayPath + "/auth?email=" + username)
                    .setMethod("GET")
                    .sendRequest();

            if (response.getStatusCode() != 200) {
                ExceptionResponse exception = mapper.convertValue(response.getResponse(),
                        ExceptionResponse.class);
                throw new ApiException(exception.getStatusCode(),
                        exception.getMessage());
            }

            UserResponse user = new ObjectMapper().readValue(response.getResponse(),
                    UserResponse.class);

            return new User(user.getEmail(),
                    "123",
                    Arrays.stream(user.getRoles())
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                            .toList());

        } catch (ProtocolException | JsonProcessingException e) {
            throw new AppException(e.getLocalizedMessage());
        }
    }
}
