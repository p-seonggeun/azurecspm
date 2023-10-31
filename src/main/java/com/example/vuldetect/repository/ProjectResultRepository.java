package com.example.vuldetect.repository;

import com.example.vuldetect.domain.DiagnosisResult;
import com.example.vuldetect.domain.Project;
import com.example.vuldetect.domain.ProjectResult;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectResultRepository {

    private final EntityManager em;

    public void save(ProjectResult projectResult) {
        em.persist(projectResult);
    }

    public void delete(List<ProjectResult> projectResults) {
        for (ProjectResult projectResult : projectResults) {
            em.remove(projectResult);
        }
    }

    public List<ProjectResult> findAll() {
        return em.createQuery("select pr from ProjectResult pr", ProjectResult.class)
                .getResultList();
    }

    public List<ProjectResult> findAllByProjectId(Long projectId) {
        return em.createQuery("SELECT pr FROM ProjectResult pr " +
                        "INNER JOIN pr.project p " +
                        "WHERE p.id = :projectId", ProjectResult.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public List<Long> findProjectResultIdByProjectId(Long projectId) {
        return em.createQuery("SELECT pr.id FROM ProjectResult pr " +
                        "WHERE pr.project.id = :projectId", Long.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public ProjectResult findProjectResultByProjectIdAndProjectRound(Long projectId, int round) {
        return em.createQuery("SELECT pr FROM ProjectResult as pr" +
                        " WHERE pr.project.id = :projectId" +
                        " and pr.round = :round", ProjectResult.class)
                .setParameter("projectId", projectId)
                .setParameter("round", round)
                .getSingleResult();
    }


    public ProjectResult findById(Long id) {
        return em.createQuery("select pr from ProjectResult pr where pr.id = : id", ProjectResult.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
