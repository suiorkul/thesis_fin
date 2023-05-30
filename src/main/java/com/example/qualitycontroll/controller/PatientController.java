package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.dal.entity.User;
import com.example.qualitycontroll.dal.enums.Role;
import com.example.qualitycontroll.dal.repository.DepartmentRepository;
import com.example.qualitycontroll.dal.repository.PatientRepository;
import com.example.qualitycontroll.dal.repository.UserRepository;
import com.example.qualitycontroll.service.MailService;
import com.example.qualitycontroll.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Controller
@RequestMapping("patients")
@RequiredArgsConstructor
public class PatientController {

    private final ModelConfig modelConfig;
    private final PatientService patientService;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final HttpServletRequest httpServletRequest;
    private final PatientRepository patientRepository;
    private final PasswordEncoder encoder;

    @GetMapping
    public String index() {
        return "redirect:/patients/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        modelConfig.configCommonAttributes(model);
        User user = userRepository.findByUsername(httpServletRequest.getUserPrincipal().getName());
        Page<Patient> page = user.getRole().equals(Role.PATIENT) ?
                new PageImpl<>(patientService.getList(pageNumber).stream()
                        .filter(patient -> patient.getFirstName().equals(user.getFirstname()) ||
                                user.getLastname().equals(patient.getLastName())).collect(Collectors.toList()))
                : patientService.getList(pageNumber);
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "patients/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctors", userRepository.findAllByRoleIs(Role.DOCTOR));
        return "patients/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("patient", patientService.get(id));
        model.addAttribute("doctors", userRepository.findAllByRoleIs(Role.DOCTOR));
        return "patients/form";
    }

    @PostMapping(value = "/save")
    public String save(Patient patient, final RedirectAttributes ra,
                       @RequestParam String departmentName,
                       @RequestParam Long doctorId, @RequestParam String username,
                       @RequestParam String password) {
        patient.setDepartment(departmentRepository.getDepartmentByName(departmentName));
        patient.setDoctor(userRepository.findById(doctorId).get());
        mailService.sendRegistrationSuccess(patient, username, password);
        userRepository.save(User.builder()
                        .username(username)
                        .password(encoder.encode(password))
                        .firstname(patient.getFirstName())
                        .lastname(patient.getLastName())
                        .email(patient.getEmail())
                        .phoneNumber(patient.getPhoneNumber())
                        .active(true)
                        .role(Role.PATIENT)
                .build());
        patientService.save(patient);
        ra.addFlashAttribute("successFlash", "User successfully saved");
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        patientService.delete(id);
        return "redirect:/patients";
    }

}
