package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import com.example.qualitycontroll.dal.entity.Department;
import com.example.qualitycontroll.dal.entity.Patient;
import com.example.qualitycontroll.dal.repository.DepartmentRepository;
import com.example.qualitycontroll.service.DepartmentService;
import com.example.qualitycontroll.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final ModelConfig modelConfig;
    private final DepartmentService departmentService;

    @GetMapping
    public String index() {
        return "redirect:/departments/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        modelConfig.configCommonAttributes(model);
        Page<Department> page = departmentService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "departments/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("department", new Department());
        return "departments/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("department", departmentService.get(id));
        return "departments/form";
    }

    @PostMapping(value = "/save")
    public String save(Department department, final RedirectAttributes ra) {
        departmentService.save(department);
        ra.addFlashAttribute("successFlash", "User successfully saved");
        return "redirect:/departments";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        departmentService.delete(id);
        return "redirect:/departments";

    }

}
