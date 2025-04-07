package com.spring.evalapi;

import com.spring.evalapi.entity.Objective;
import com.spring.evalapi.service.ObjectiveService;
import com.spring.evalapi.controller.ObjectiveController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ObjectiveControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ObjectiveService objectiveService;

    @InjectMocks
    private ObjectiveController objectiveController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(objectiveController).build();
    }

    @Test
    public void testAssignObjectiveByUserId() throws Exception {
        // Mock the service layer behavior
        Objective objective = new Objective();
        objective.setId(1L);
        objective.setTitle("Test Objective");
        objective.setDescription("Complete the project");
        objective.setManagerId(1L);
        objective.setDeadline(LocalDate.of(2025, 12, 31));
        objective.setTeamId(1L);
        objective.setCycleId(1L);

        when(objectiveService.assignObjectiveByUserId(any(Objective.class))).thenReturn(objective);


        mockMvc.perform(post("/objectives")
                        .contentType("application/json")
                        .content("{ \"title\": \"Test Objective\", \"assignedUserId\": 1, \"description\": \"Complete the project\", \"managerId\": 1, \"deadline\": \"2025-12-31\", \"TeamId\": 1, \"cycleId\": 1 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Objective"));

        verify(objectiveService, times(1)).assignObjectiveByUserId(any(Objective.class));
    }

    @Test
    public void testFindObjectiveByAssignId() throws Exception {

        Objective objective = new Objective();
        objective.setId(1L);
        objective.setTitle("Test Objective");

        List<Objective> objectives = Collections.singletonList(objective);
        when(objectiveService.findAllByAssignedUserId(1L)).thenReturn(objectives);

        mockMvc.perform(get("/objectives/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Objective"));


        verify(objectiveService, times(1)).findAllByAssignedUserId(1L);
    }

    @Test
    public void testUpdateObjective() throws Exception {

        Objective objective = new Objective();
        objective.setId(1L);
        objective.setTitle("Updated Objective");

        when(objectiveService.UpdateByAssignId(eq(1L), any(Objective.class))).thenReturn(objective);


        mockMvc.perform(put("/objectives/1")
                        .contentType("application/json")
                        .content("{ \"title\": \"Updated Objective\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Objective"));


        verify(objectiveService, times(1)).UpdateByAssignId(eq(1L), any(Objective.class));
    }

    @Test
    public void testDeleteObjective() throws Exception {

        when(objectiveService.deleteByAssignIdAndObjectiveId(eq(1L), eq(1L))).thenReturn("Deleted successfully");

        mockMvc.perform(delete("/objectives/1/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Deleted successfully"));

        verify(objectiveService, times(1)).deleteByAssignIdAndObjectiveId(eq(1L), eq(1L));
    }

    @Test
    public void testCompleteObjective() throws Exception {

        Objective objective = new Objective();
        objective.setId(1L);
        objective.setTitle("Completed Objective");

        when(objectiveService.completeObjective(1L)).thenReturn(objective);
        mockMvc.perform(put("/objectives/state/1/complete"))
                .andExpect(status().isNoContent());

        verify(objectiveService, times(1)).completeObjective(1L);
    }
}

