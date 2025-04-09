package com.spring.evalapi.service;

import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.dto.TeamDto;
import com.spring.evalapi.dto.TeamMemberDto;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final WebClient webClient = WebClient.create("http://localhost:8082/teams");


    public TeamDto getTeamByMemberId(Long memberId) {
        TeamDto team = webClient.get()
                .uri("/team-members?memberId=" + memberId)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new NotFoundException("No teams found for memberId: " + memberId)))
                .bodyToMono(TeamDto.class)
                .block();
        return team;
    }

    public TeamDto getTeamId(Long id) {
        TeamDto team= webClient.get()
                .uri("/"+id)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new NotFoundException("No teams found"))
                )
                .bodyToMono(TeamDto.class)
                .block();
        return team;
    }



}
