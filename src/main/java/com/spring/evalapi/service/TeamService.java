package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.NotFoundException;
import com.spring.evalapi.dto.TeamDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TeamService {

    private final WebClient webClient = WebClient.create("http://localhost:8082/teams");
    public TeamDto getTeamById(Long id){
        TeamDto teamDto = webClient.get()
                .uri("/?id=" + id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        response.createException().flatMap(error ->
                                Mono.error(new NotFoundException("Team not found with id: " + id))
                        )
                )
                .bodyToMono(TeamDto.class)
                .block();
        if (teamDto == null) {
            throw new NotFoundException("Team not found with id: " + id);
        }
        return teamDto;
    }

}
