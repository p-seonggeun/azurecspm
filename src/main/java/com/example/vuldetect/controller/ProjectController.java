package com.example.vuldetect.controller;

import com.example.vuldetect.domain.*;
import com.example.vuldetect.repository.ComplianceRepository;
import com.example.vuldetect.service.DiagnosisResultService;
import com.example.vuldetect.service.ProjectResultService;
import com.example.vuldetect.service.ProjectService;
import com.example.vuldetect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectResultService projectResultService;
    private final DiagnosisResultService diagnosisResultService;
    private final ComplianceRepository complianceRepository;

    @GetMapping
    public String projects(Model model) {
        List<Project> projects = projectService.findAllProject();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/add")
    public String projectAddForm(Model model) {
        List<User> users = userService.findUsers();
        List<Compliance> compliances = complianceRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("compliances", compliances);
        return "projectAddForm";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute("projectName") String projectName,
                             @ModelAttribute("userId") Long userId,
                             @ModelAttribute("complianceId") Long complianceId) {
        System.out.println("projectName = " + projectName);
        System.out.println("userId = " + userId);
        System.out.println("complianceId = " + complianceId);
        User findUser = userService.findOne(userId);
        Compliance findCompliance = complianceRepository.findOne(complianceId);
        Project project = new Project(projectName, findUser, findCompliance);
        projectService.createProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{projectId}")
    public String projectEditForm(@PathVariable Long projectId, Model model) {
        System.out.println("projectId = " + projectId);
        Project project = projectService.findProject(projectId);
        model.addAttribute("project", project);
        return "projectEditForm";
    }

    @PostMapping("/edit/{projectId}")
    public String editProject(@PathVariable Long projectId, @ModelAttribute("name") String name) {
        Project findProject = projectService.findProject(projectId);
        projectService.updateProject(findProject, name);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable("projectId") Long projectId) {
        System.out.println("projectId = " + projectId);
        Project findProject = projectService.findProject(projectId);

        List<ProjectResult> projectResults = projectResultService.findProjectResultsByProject(findProject);
        for (ProjectResult projectResult : projectResults) {
            diagnosisResultService.deleteDiagnosisResults(projectResult.getId());
        }
        projectResultService.deleteProjectResults(projectId);
        projectService.deleteProject(findProject);
        return "redirect:/projects";
    }
}
