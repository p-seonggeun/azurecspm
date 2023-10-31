package com.example.vuldetect.repository;

import com.example.vuldetect.domain.Compliance;
import com.example.vuldetect.domain.DiagnosisItem;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ComplianceRepository {

    private final EntityManager em;

    public void save(Compliance compliance) {
        em.persist(compliance);
    }

    public Compliance findOne(Long id) {
        return em.find(Compliance.class, id);
    }

    public List<Compliance> findAll() {
        return em.createQuery("select c from Compliance c", Compliance.class)
                .getResultList();
    }

    public Compliance findByName(String name) {
        return em.find(Compliance.class, name);
    }
}
