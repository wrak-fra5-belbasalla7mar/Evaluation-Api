package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.repository.CycleRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {
    private final CycleService cycleService;
    private final RestTemplate restTemplate;
    public ReportService(CycleService cycleService, RestTemplate restTemplate) {
        this.cycleService = cycleService;
        this.restTemplate = restTemplate;
    }

    public byte[] generateCycleReport(Long id) {
        Cycle cycle = cycleService.cycleByID(id);
        if (cycle == null) {
            throw new CycleNotFoundException(String.format("Cycle with the ID: %d not found", id));
        }
        String url = String.format("http://localhost:8081/api/reports/download/cycle/%d", id);
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return response.getBody();
    }

}
