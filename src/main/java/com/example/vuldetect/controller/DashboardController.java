package com.example.vuldetect.controller;

import com.example.vuldetect.domain.DiagnosisResult;
import com.example.vuldetect.domain.Project;
import com.example.vuldetect.domain.ProjectResult;
import com.example.vuldetect.dto.DiagnosisItemDTO;
import com.example.vuldetect.dto.DiagnosisResultDTO;
import com.example.vuldetect.service.DiagnosisResultService;
import com.example.vuldetect.service.ProjectResultService;
import com.example.vuldetect.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectService projectService;
    private final ProjectResultService projectResultService;
    private final DiagnosisResultService diagnosisResultService;

    @GetMapping
    public String dashboard(Model model) {
        List<Project> projects = projectService.findAllProject();
        model.addAttribute("projects", projects);
        return "dashboard";
    }

    @GetMapping("/diagnosis/{projectId}")
    public String diagnosisResultProject(@PathVariable Long projectId, Model model) throws IOException, InterruptedException {
        Project findProject = projectService.findProject(projectId);
        projectService.buttonClick(findProject.getName());
        List<Project> projects = projectService.findAllProject();
        model.addAttribute("projects", projects);
        return "redirect:/dashboard";
    }

    @GetMapping("/{projectId}/{projectRound}")
    public String selectedProjectAndRound(@PathVariable Long projectId, @PathVariable int projectRound, Model model) throws JsonProcessingException {
        List<Project> projects = projectService.findAllProject();
        model.addAttribute("projects", projects);

        Project findProject = projectService.findProject(projectId);
        model.addAttribute("selectedProjectName", findProject.getName());
        model.addAttribute("selectedProjectId", projectId);
        model.addAttribute("selectedProjectRound", projectRound);

        ProjectResult findProjectResult = projectResultService.findByProjectIdAndRound(projectId, projectRound);
        List<DiagnosisResult> findDiagnosisResultsByProjectResultId = diagnosisResultService.findAllDiagnosisResultByProjectResultId(findProjectResult.getId());
        diagnosisResultService.setJsonNodeDiagnosisResult(findDiagnosisResultsByProjectResultId);
        model.addAttribute("findProjectResult", findProjectResult);
        model.addAttribute("findDiagnosisResults", findDiagnosisResultsByProjectResultId);

        List<DiagnosisResultDTO> diagnosisResultDTOs = diagnosisResultService.getDiagnosisResultDTO(findDiagnosisResultsByProjectResultId);
        model.addAttribute("diagnosisResultDTOs", diagnosisResultDTOs);

        List<DiagnosisResult> findDiagnosisResults = diagnosisResultService.findAllDiagnosisResult();
        List<Long> projectResultIdByProjectId = projectResultService.findProjectResultIdByProjectId(projectId);

        List<DiagnosisItemDTO> diagnosisItemDTOs = new ArrayList<>();
        for (Long i : projectResultIdByProjectId) {
            for (DiagnosisResult findDiagnosisResult : findDiagnosisResults) {
                if (findDiagnosisResult.getProjectResult().getId().equals(i)) {
                    DiagnosisItemDTO diagnosisItemDTO = new DiagnosisItemDTO(
                            findDiagnosisResult.getName(),
                            findDiagnosisResult.getProjectResult().getRound(),
                            findDiagnosisResult.getResult(),
                            findDiagnosisResult.getVulnerability()
                    );
                    diagnosisItemDTOs.add(diagnosisItemDTO);
                }
            }
        }
        model.addAttribute("diagnosisItemDTOs", diagnosisItemDTOs);


        /**
         * P, L, M, H 순서로
         */
        List<Integer> resultsCount = diagnosisResultService.getResultsCount(findProjectResult);
        model.addAttribute("resultsCount", resultsCount);

        List<Integer> categoryPassedCount = diagnosisResultService.getCategoryPassedCount(findProjectResult);
        model.addAttribute("categoryPassedCount", categoryPassedCount);

        List<Integer> categoryCount = diagnosisResultService.getCategoryCount(findProjectResult);
        model.addAttribute("categoryCount", categoryCount);

        List<Integer> categoryFailCount = diagnosisResultService.getCategoryFailCount(findProjectResult);
        model.addAttribute("categoryFailCount", categoryFailCount);

        return "dashboard";
    }
}
