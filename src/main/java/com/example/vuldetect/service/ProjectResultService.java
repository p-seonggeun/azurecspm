package com.example.vuldetect.service;

import com.example.vuldetect.domain.Project;
import com.example.vuldetect.domain.ProjectResult;
import com.example.vuldetect.repository.ProjectResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectResultService {

    private final ProjectResultRepository projectResultRepository;

    public List<ProjectResult> findAllProjectResult() {
        List<ProjectResult> projectResults = projectResultRepository.findAll();
        return projectResults;
    }
    public List<ProjectResult> findAllProjectResultById(Long projectId) {
        List<ProjectResult> projectResults = projectResultRepository.findAllByProjectId(projectId);
        return projectResults;
    }

    public List<Long> findProjectResultIdByProjectId(Long projectId) {
        List<Long> projectResultIdByProjectId = projectResultRepository.findProjectResultIdByProjectId(projectId);
        return projectResultIdByProjectId;
    }

    public ProjectResult findByProjectIdAndRound(Long projectId, int round) {
        ProjectResult findProjectResult = projectResultRepository.findProjectResultByProjectIdAndProjectRound(projectId, round);
        return findProjectResult;
    }


    public List<ProjectResult> findProjectResultsByProject(Project project) {
        List<ProjectResult> projectResults = projectResultRepository.findAllByProjectId(project.getId());
        return projectResults;
    }

    public void deleteProjectResults(Long projectId) {
        List<ProjectResult> findResults = projectResultRepository.findAllByProjectId(projectId);
        projectResultRepository.delete(findResults);
    }
}
