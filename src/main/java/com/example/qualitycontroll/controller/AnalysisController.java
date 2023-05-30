package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import com.example.qualitycontroll.dal.entity.Analysis;
import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.dal.enums.Role;
import com.example.qualitycontroll.dal.repository.AnalysisRepository;
import com.example.qualitycontroll.dal.repository.DepartmentRepository;
import com.example.qualitycontroll.dal.repository.PatientRepository;
import com.example.qualitycontroll.dal.repository.UserRepository;
import com.example.qualitycontroll.service.AnalysisService;
import com.example.qualitycontroll.service.MailService;
import com.example.qualitycontroll.service.PatientService;
import com.example.qualitycontroll.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("analyses")
@RequiredArgsConstructor
public class AnalysisController {

    private final ModelConfig modelConfig;
    private final AnalysisService analysisService;
    private final PatientRepository patientRepository;
    private final AnalysisRepository analysisRepository;
    private final SearchController searchController;
    private final StorageService storageService;
    private final MailService mailService;

    private final UserRepository userRepository;

    private final HttpServletRequest request;

    @GetMapping
    public String index() {
        return "redirect:/analyses/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        modelConfig.configCommonAttributes(model);
        Page<Analysis> page = analysisService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("patients", patientRepository.findAll());

        return "analyses/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("analysis", new Analysis());
        return "analyses/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("analysis", analysisService.get(id));
        return "analyses/form";
    }

    @PostMapping(value = "/save")
    public String save(Model model, Analysis analysis, final RedirectAttributes ra, @RequestParam String fio,
                       @RequestParam MultipartFile file1) {
        String[] fios = fio.split(" ");
        analysis.setPatient(patientRepository.getPatientByFirstNameAndLastNameAndPatronymic(fios[0], fios[1], fios[2]));
        analysis.setDepartmentDocument(storageService.uploadFile(file1));
        mailService.sendAnalysis(analysis);
        analysisService.save(analysis);
        ra.addFlashAttribute("successFlash", "User successfully saved");
        return searchController.search(model, fios[0], fios[1], fios[2]);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Patient patient = analysisRepository.findById(id).orElseThrow().getPatient();
        analysisService.delete(id);
        model.addAttribute("firstName", patient.getFirstName());
        model.addAttribute("lastName", patient.getLastName());
        model.addAttribute("patronymic", patient.getPatronymic());
        model.addAttribute("list", analysisRepository
                .findAllByPatient(patientRepository
                        .findByFirstNameAndLastNameAndPatronymic(patient.getFirstName(), patient.getLastName(), patient.getPatronymic())));
        return "analyses/search";
    }

}
