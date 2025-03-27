package com.spring.evalapi.service;

import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.dto.TeamDto;
import com.spring.evalapi.dto.TeamMemberDto;
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
    public TeamDto getTeamByUserId(Long userId) {
        TeamDto[] allTeams = webClient.get()
                .uri("")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response ->
                        Mono.error(new NotFoundException("No teams found"))
                )
                .bodyToMono(TeamDto[].class)
                .block();

        if (allTeams == null || allTeams.length == 0) {
            throw new NotFoundException("No teams found ");
        }

        for (TeamDto team : allTeams) {
            for (TeamMemberDto member : team.getMembers()) {
                if (member.getUserId().equals(userId)) {
                    return team;         }
            }
        }
        throw new NotFoundException("No team found for user with ID: " + userId);
    }
}
