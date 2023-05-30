package com.example.qualitycontroll.config;

import com.example.qualitycontroll.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Configuration
@RequiredArgsConstructor
public class ModelConfig {
    private final HttpServletRequest request;
    private final UserRepository userRepository;
    public void configCommonAttributes(Model model) {
        model.addAttribute("name", getUsername(request));
        model.addAttribute("role", userRepository.findByUsername(getUsername(request)).getRole().toString());
    }

    private String getUsername(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
}
