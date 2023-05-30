package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final ModelConfig modelConfig;

    @GetMapping("/")
    public String index(Model model) {
        modelConfig.configCommonAttributes(model);
        return "dashboard/index";
    }

}

