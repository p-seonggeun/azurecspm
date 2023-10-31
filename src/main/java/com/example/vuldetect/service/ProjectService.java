package com.example.vuldetect.service;

import com.example.vuldetect.domain.DiagnosisResult;
import com.example.vuldetect.domain.Project;
import com.example.vuldetect.domain.ProjectResult;
import com.example.vuldetect.domain.User;
import com.example.vuldetect.repository.DiagnosisResultRepository;
import com.example.vuldetect.repository.ProjectRepository;
import com.example.vuldetect.repository.ProjectResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectResultRepository projectResultRepository;
    private final DiagnosisResultRepository diagnosisResultRepository;
    private final DiagnosisResultService diagnosisResultService;

    public void createProject(Project project) {
        validateDuplicateProject(project);
        projectRepository.save(project);
    }

    private void validateDuplicateProject(Project project) {
        Project findProject = projectRepository.findByName(project.getName());
        if (findProject != null) {
            throw new IllegalStateException("이미 존재하는 이름의 프로젝트입니다.");
        }
    }

    public void deleteProject(Project project) {
        Project findProject = projectRepository.findOne(project.getId());
        projectRepository.delete(findProject);
    }

    public void updateProject(Project project, String name) {
        Project findProject = projectRepository.findByName(project.getName());
        findProject.setName(name);
        projectRepository.update(findProject);
    }

    public List<Project> findAllProject() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }


    public Project findProject(String name) {
        Project project = projectRepository.findByName(name);
        return project;
    }
    public Project findProject(Long id) {
        Project project = projectRepository.findOne(id);
        return project;
    }

    public void buttonClick(String projectName) throws IOException, InterruptedException {
        Project findProject = projectRepository.findByName(projectName);
        findProject.diagnosisCounter();
        ProjectResult projectResult = new ProjectResult(findProject);
        diagnosisResultService.diagnosis(); // 진단을 수행한 것
        List<DiagnosisResult> diagnosisResults = diagnosisResultRepository.findNull();
        projectResult.addDiagnosisResult(diagnosisResults);
        projectResult.calculateScore();
        projectResultRepository.save(projectResult);
    }
}
