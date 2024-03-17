package lk.ijse.dep11.edupanel.repository.custom.impl;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.entity.SuperEntity;
import lk.ijse.dep11.edupanel.repository.custom.LinkedInRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class LinkedInRepositoryImpl implements LinkedInRepository {

    private EntityManager em;
    @Override
    public LinkedIn save(LinkedIn entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public void update(SuperEntity entity) {
        em.merge(entity);
    }

    @Override
    public void deleteById(Lecturer pk) {
        em.remove(em.find(LinkedIn.class, pk));
    }

    @Override
    public boolean existsById(Lecturer pk) {
        return findById(pk).isPresent();
    }

    @Override
    public Optional<LinkedIn> findById(Lecturer pk) {
        return Optional.ofNullable(em.find(LinkedIn.class, pk));
    }

    @Override
    public List<LinkedIn> findAll() {
        return em.createQuery("SELECT li FROM LinkedIn li", LinkedIn.class).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(li) FROM LinkedIn li", Long.class).getSingleResult();
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
