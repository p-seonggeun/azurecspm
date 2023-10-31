package com.example.vuldetect.repository;

import com.example.vuldetect.domain.DiagnosisItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiagnosisItemRepository {

    private final EntityManager em;

    public void save(DiagnosisItem diagnosisItem) {
        em.persist(diagnosisItem);
    }

    public List<DiagnosisItem> findAll() {
        return em.createQuery("select d from DiagnosisItem d", DiagnosisItem.class)
                .getResultList();
    }

    public DiagnosisItem findByCode(String code) {
        return em.createQuery("select d from DiagnosisItem d where d.code = : code", DiagnosisItem.class)
                .setParameter("code", code)
                .getSingleResult();
    }
}
