package com.spring.evalapi.service;
import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class UserService {
    private final WebClient webClient = WebClient.create("http://localhost:8080/manager");
    public UserDto getUserById(Long id) {
        UserDto user = webClient.get()
                .uri("/find?id=" + id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        response.createException().flatMap(error ->
                                Mono.error(new NotFoundException("User not found with id: " + id))
                        )
                )
                .bodyToMono(UserDto.class)
                .block();
        if (user == null) {
            throw new NotFoundException("User not found with id: " + id);
        }
        return user;
    }

}
