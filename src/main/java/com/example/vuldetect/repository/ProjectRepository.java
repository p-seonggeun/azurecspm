package com.example.vuldetect.repository;


import com.example.vuldetect.domain.DiagnosisResult;
import com.example.vuldetect.domain.Project;
import com.example.vuldetect.domain.ProjectResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project) {
        em.persist(project);
    }

    public void update(Project project) {
        em.merge(project);
    }

    public void delete(Project project) {
        em.remove(project);
    }

    public List<Project> findAll() {
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

    public Project findOne(Long id) {
        return em.find(Project.class, id);
    }

    public Project findByName(String name) {
        try {
            return em.createQuery("select p from Project p where p.name = :name", Project.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            // 쿼리 결과가 없을 경우 처리할 코드를 여기에 추가
            return null; // 또는 예외를 다시 던지거나 다른 처리를 수행
        }
    }
}
