package com.example.vuldetect.controller;

import com.example.vuldetect.domain.Compliance;
import com.example.vuldetect.repository.ComplianceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/compliance")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceRepository complianceRepository;

    @GetMapping
    public String compliances(Model model) {
        Compliance compliance = complianceRepository.findOne(1L);
        model.addAttribute("compliance", compliance);
        return "compliance";
    }
}
