package com.example.vuldetect.repository;

import com.example.vuldetect.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }
    public User findByName(String name) {
        try {
            return em.createQuery("select u from User u where u.username = :name", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
