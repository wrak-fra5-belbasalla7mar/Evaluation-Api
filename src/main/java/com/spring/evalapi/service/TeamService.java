package com.spring.evalapi.service;

import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.dto.TeamDto;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class TeamService {

    private final WebClient webClient = WebClient.create("http://localhost:8082/teams");


    public TeamDto getTeamByMemberId(Long memberId) {
        return webClient.get()
                .uri("/team-members?memberId=" + memberId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new NotFoundException("No teams found for memberId: " + memberId)))
                .bodyToMono(TeamDto.class)
                .block();
    }

    public TeamDto getTeamId(Long id) {
        return webClient.get()
                .uri("/"+id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new NotFoundException("No teams found"))
                )
                .bodyToMono(TeamDto.class)
                .block();
    }



}
