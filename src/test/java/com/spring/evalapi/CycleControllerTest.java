package com.spring.evalapi;
import com.spring.evalapi.controller.CycleController;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.service.CycleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
public class CycleControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CycleService cycleService;

    @InjectMocks
    private CycleController cycleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cycleController).build();
    }

    @Test
    public void testCreateCycle() throws Exception {
        Cycle cycle = new Cycle();
        cycle.setId(1L);
        cycle.setName("Test Cycle");

        when(cycleService.addCycle(any(Cycle.class))).thenReturn(cycle);

        mockMvc.perform(post("/cycles")
                        .contentType("application/json")
                        .content("{\"name\": \"Test Cycle\", \"startDate\": \"2025-01-01\", \"endDate\": \"2025-12-31\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Cycle"));

        verify(cycleService, times(1)).addCycle(any(Cycle.class));
    }

    @Test
    public void testUpdateCycle() throws Exception {
        Cycle cycle = new Cycle();
        cycle.setId(1L);
        cycle.setName("Updated Cycle");

        when(cycleService.updateCycle(eq(1L), any(Cycle.class))).thenReturn(cycle);

        mockMvc.perform(put("/cycles/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Updated Cycle\", \"startDate\": \"2025-01-01\", \"endDate\": \"2025-12-31\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Cycle"));

        verify(cycleService, times(1)).updateCycle(eq(1L), any(Cycle.class));
    }

    @Test
    public void testGetLatestCycle() throws Exception {
        Cycle cycle = new Cycle();
        cycle.setId(1L);
        cycle.setName("Latest Cycle");

        when(cycleService.ViewTheLatestCycle()).thenReturn(cycle);

        mockMvc.perform(get("/cycles/Latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Latest Cycle"));

        verify(cycleService, times(1)).ViewTheLatestCycle();
    }

    @Test
    public void testGetAllCyclesAsc() throws Exception {
        Cycle cycle1 = new Cycle();
        cycle1.setId(1L);
        cycle1.setName("Cycle 1");

        Cycle cycle2 = new Cycle();
        cycle2.setId(2L);
        cycle2.setName("Cycle 2");

        List<Cycle> cycles = Arrays.asList(cycle1, cycle2);

        when(cycleService.findAllByOrderByStartDateAsc()).thenReturn(cycles);

        mockMvc.perform(get("/cycles/Asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(cycleService, times(1)).findAllByOrderByStartDateAsc();
    }

    @Test
    public void testGetCycleByID() throws Exception {
        Cycle cycle = new Cycle();
        cycle.setId(1L);
        cycle.setName("Cycle By ID");

        when(cycleService.cycleByID(eq(1L))).thenReturn(cycle);

        mockMvc.perform(get("/cycles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cycle By ID"));

        verify(cycleService, times(1)).cycleByID(eq(1L));
    }

    @Test
    public void testDeleteCycleById() throws Exception {
        String responseMessage = "Cycle deleted successfully";
        when(cycleService.deleteCycleById(eq(1L))).thenReturn(responseMessage);

        mockMvc.perform(delete("/cycles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(responseMessage));

        verify(cycleService, times(1)).deleteCycleById(eq(1L));
    }
}
