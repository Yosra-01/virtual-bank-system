package com.project.bank.bff_service.controller;

import com.project.bank.bff_service.dto.DashboardResponse;
import com.project.bank.bff_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bff")
@RequiredArgsConstructor
public class BffDashboardController {

    private final DashboardService dashboardService;

    // GET /bff/dashboard/{userId}
    @GetMapping("/dashboard/{userId}")
    public DashboardResponse getDashboard(@PathVariable UUID userId) {
        return dashboardService.buildDashboard(userId);
    } 
}

