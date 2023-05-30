package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.dal.repository.AnalysisRepository;
import com.example.qualitycontroll.dal.repository.DepartmentRepository;
import com.example.qualitycontroll.dal.repository.PatientRepository;
import com.example.qualitycontroll.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class SearchController {

    private final ModelConfig modelConfig;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final AnalysisRepository analysisRepository;

    @GetMapping("/search")
    public String search(Model model, @RequestParam String keyWord, @RequestParam String departmentName) {
        List<Patient> patients = patientRepository.findAllByDepartmentOrderByCreatedDesc(departmentRepository.getDepartmentByName(departmentName));
        modelConfig.configCommonAttributes(model);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("list", departmentName.equals("") ? patientRepository.findAllByFirstName(keyWord) : patients);
        return "patients/search";
    }

    @GetMapping("/search-analyses")
    public String search(Model model , @RequestParam String firstName,
                         @RequestParam String lastName, @RequestParam String patronymic) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("patronymic", patronymic);
        model.addAttribute("list", analysisRepository
                .findAllByPatient(patientRepository
                        .findByFirstNameAndLastNameAndPatronymic(firstName, lastName, patronymic)));
        return "analyses/search";
    }
}
