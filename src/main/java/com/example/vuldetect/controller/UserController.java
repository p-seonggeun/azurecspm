package com.example.vuldetect.controller;

import com.example.vuldetect.service.ComplianceService;
import com.example.vuldetect.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String User() {
        log.info("user controller");
        return "redirect:/dashboard";
    }

    @PostMapping("/user")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        int loginCode = userService.login(username, password);
        if (loginCode == 1) {
            return "redirect:/projects";
        }
        return "redirect:/";
    }


}
