package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import com.example.qualitycontroll.dal.entity.Appointment;
import com.example.qualitycontroll.dal.enums.Role;
import com.example.qualitycontroll.dal.repository.UserRepository;
import com.example.qualitycontroll.service.AppointmentService;
import com.example.qualitycontroll.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final ModelConfig modelConfig;
    private final MailService mailService;
    private final AppointmentService appointmentService;

    private final HttpServletRequest request;
    private final UserRepository userRepository;

    @GetMapping
    public String index() {
        return "redirect:/appointments/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        modelConfig.configCommonAttributes(model);
        Page<Appointment> page = appointmentService.getByPatientOrDoctor(request.getUserPrincipal().getName(), pageNumber);
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        return "appointments/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", userRepository.findAllByRoleIs(Role.DOCTOR));
        model.addAttribute("patients", userRepository.findAllByRoleIs(Role.PATIENT));
        return "appointments/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("appointment", appointmentService.get(id));
        model.addAttribute("doctors", userRepository.findAllByRoleIs(Role.DOCTOR));
        model.addAttribute("patients", userRepository.findAllByRoleIs(Role.PATIENT));
        return "appointments/form";
    }

    @PostMapping(value = "/save")
    public String save(Appointment appointment, final RedirectAttributes ra,
                       @RequestParam Long patientId,
                       @RequestParam Long doctorId) {
        appointment.setPatient(userRepository.findById(patientId).get());
        appointment.setDoctor(userRepository.findById(doctorId).get());
        mailService.sendAppointmentSuccess(appointment);
        appointmentService.save(appointment);
        ra.addFlashAttribute("successFlash", "User successfully saved");
        return "redirect:/appointments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        mailService.sendAppointmentCanceled(appointmentService.get(id));
        appointmentService.delete(id);
        return "redirect:/appointments";
    }
}
