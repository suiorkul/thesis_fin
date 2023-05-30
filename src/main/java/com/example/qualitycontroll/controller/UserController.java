package com.example.qualitycontroll.controller;

import com.example.qualitycontroll.config.ModelConfig;
import com.example.qualitycontroll.dal.entity.User;
import com.example.qualitycontroll.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final ModelConfig modelConfig;

    @GetMapping
    public String index() {
        return "redirect:/users/1";
    }

    @GetMapping(value = "/{pageNumber}")
    public String list(@PathVariable Integer pageNumber, Model model) {
        modelConfig.configCommonAttributes(model);
        Page<User> page = userService.getList(pageNumber);

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());

        model.addAttribute("list", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);

        return "users/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("user", new User());
        return "users/form";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        modelConfig.configCommonAttributes(model);
        model.addAttribute("user", userService.get(id));
        return "users/form";

    }

    @PostMapping(value = "/save")
    public String save(User user, final RedirectAttributes ra) {
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        ra.addFlashAttribute("successFlash", "User successfully saved");
        return "redirect:/users";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        userService.delete(id);
        return "redirect:/users";

    }
}
