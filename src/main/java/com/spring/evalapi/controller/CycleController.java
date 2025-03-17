package com.spring.evalapi.controller;


import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.mapper.CycleMapper;
import com.spring.evalapi.service.CycleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cycle")
public class CycleController {

    private final CycleService cycleService;
    private CycleMapper cycleMapper;
    public CycleController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @PostMapping("")
    public Cycle addCycle(@RequestBody Cycle cycle){
        cycleService.addCycle(cycle);

        return cycle;
    }

    @GetMapping("")
    public Cycle viewCycle(){
        return cycleService.viewCycle();
    }

    @DeleteMapping("/{id}")
    public String deleteCycle(@PathVariable("id") int id){
        cycleService.deleteCycle(id);
        return "deleted";
    }
}
