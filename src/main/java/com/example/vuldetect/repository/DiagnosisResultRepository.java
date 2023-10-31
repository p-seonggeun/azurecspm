package com.example.vuldetect.repository;

import com.example.vuldetect.domain.DiagnosisResult;
import com.example.vuldetect.domain.ProjectResult;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiagnosisResultRepository {

    private final EntityManager em;

    public void save(DiagnosisResult diagnosisResult) {
        em.persist(diagnosisResult);
    }

    public List<DiagnosisResult> findAll() {
        return em.createQuery("select dr from DiagnosisResult dr", DiagnosisResult.class)
                .getResultList();
    }

    public List<DiagnosisResult> findNull() {
        return em.createQuery("select dr from DiagnosisResult dr where dr.projectResult.id = NULL", DiagnosisResult.class)
                .getResultList();
    }

    public List<DiagnosisResult> findAllByProjectResultId(Long projectResultId) {
        return em.createQuery("select dr from DiagnosisResult dr where dr.projectResult.id = : projectResultId", DiagnosisResult.class)
                .setParameter("projectResultId", projectResultId)
                .getResultList();
    }

    public void delete(List<DiagnosisResult> diagnosisResults) {
        for (DiagnosisResult diagnosisResult : diagnosisResults) {
            em.remove(diagnosisResult);
        }
    }

    public DiagnosisResult findByName(String name) {
        return em.createQuery("select dr from DiagnosisResult dr where dr.name = : name", DiagnosisResult.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<DiagnosisResult> findVulnerabilityIsHByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                "from DiagnosisResult dr " +
                "where dr.vulnerability = : vulnerability " +
                "and dr.projectResult.id = : projectResultId", DiagnosisResult.class)
                .setParameter("vulnerability", "High")
                .setParameter("projectResultId", projectResult.getId())
                .getResultList();
    }
    public List<DiagnosisResult> findVulnerabilityIsMByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                "from DiagnosisResult dr " +
                "where dr.vulnerability = : vulnerability " +
                "and dr.projectResult.id = : projectResultId", DiagnosisResult.class)
                .setParameter("vulnerability", "Medium")
                .setParameter("projectResultId", projectResult.getId())
                .getResultList();
    }
    public List<DiagnosisResult> findVulnerabilityIsLByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                "from DiagnosisResult dr " +
                "where dr.vulnerability = : vulnerability " +
                "and dr.projectResult.id = : projectResultId", DiagnosisResult.class)
                .setParameter("vulnerability", "Low")
                .setParameter("projectResultId", projectResult.getId())
                .getResultList();
    }

    public List<DiagnosisResult> findVulnerabilityIsHAndVulByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                "from DiagnosisResult dr " +
                "where dr.vulnerability = : vulnerability " +
                "and dr.projectResult.id = : projectResultId " +
                "and dr.result = : result", DiagnosisResult.class)
                .setParameter("vulnerability", "High")
                .setParameter("projectResultId", projectResult.getId())
                .setParameter("result", "취약")
                .getResultList();
    }
    public List<DiagnosisResult> findVulnerabilityIsMAndVulByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                "from DiagnosisResult dr " +
                "where dr.vulnerability = : vulnerability " +
                "and dr.projectResult.id = : projectResultId " +
                "and dr.result = : result", DiagnosisResult.class)
                .setParameter("vulnerability", "Medium")
                .setParameter("projectResultId", projectResult.getId())
                .setParameter("result", "취약")
                .getResultList();
    }
    public List<DiagnosisResult> findVulnerabilityIsLAndVulByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                "from DiagnosisResult dr " +
                "where dr.vulnerability = : vulnerability " +
                "and dr.projectResult.id = : projectResultId " +
                "and dr.result = : result", DiagnosisResult.class)
                .setParameter("vulnerability", "Low")
                .setParameter("projectResultId", projectResult.getId())
                .setParameter("result", "취약")
                .getResultList();
    }

    public List<DiagnosisResult> findPassedResultByProjectResult(ProjectResult projectResult) {
        return em.createQuery("select dr " +
                        "from DiagnosisResult dr " +
                        "where dr.result = : result " +
                        "and dr.projectResult.id = : projectResultId", DiagnosisResult.class)
                .setParameter("result", "양호")
                .setParameter("projectResultId", projectResult.getId())
                .getResultList();
    }

    public List<DiagnosisResult> findPassedCategoryByProjectResult(ProjectResult projectResult, String category) {
        return em.createQuery("select dr " +
                        "from DiagnosisResult dr " +
                        "where dr.category = : category " +
                        "and dr.projectResult.id = : projectResultId " +
                        "and dr.result = : result", DiagnosisResult.class)
                .setParameter("category", category)
                .setParameter("projectResultId", projectResult.getId())
                .setParameter("result", "양호")
                .getResultList();
    }
    public List<DiagnosisResult> findCategoryByProjectResult(ProjectResult projectResult, String category) {
        return em.createQuery("select dr " +
                        "from DiagnosisResult dr " +
                        "where dr.category = : category " +
                        "and dr.projectResult.id = : projectResultId ", DiagnosisResult.class)
                .setParameter("category", category)
                .setParameter("projectResultId", projectResult.getId())
                .getResultList();
    }
    public List<DiagnosisResult> findFailCategoryByProjectResult(ProjectResult projectResult, String category) {
        return em.createQuery("select dr " +
                        "from DiagnosisResult dr " +
                        "where dr.category = : category " +
                        "and dr.projectResult.id = : projectResultId " +
                        "and dr.result = : result", DiagnosisResult.class)
                .setParameter("category", category)
                .setParameter("projectResultId", projectResult.getId())
                .setParameter("result", "취약")
                .getResultList();
    }

}
