package lk.ijse.dep11.edupanel.repository.custom.impl;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.Picture;
import lk.ijse.dep11.edupanel.entity.SuperEntity;
import lk.ijse.dep11.edupanel.repository.custom.PictureRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class PictureRepositoryImpl implements PictureRepository {

    private EntityManager em;
    @Override
    public Picture save(Picture entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public void update(SuperEntity entity) {
        em.merge(entity);
    }

    @Override
    public void deleteById(Lecturer pk) {
        em.remove(em.find(Picture.class, pk));
    }

    @Override
    public boolean existsById(Lecturer pk) {
        return findById(pk).isPresent();
    }

    @Override
    public Optional<Picture> findById(Lecturer pk) {
        return Optional.ofNullable(em.find(Picture.class, pk));
    }

    @Override
    public List<Picture> findAll() {
        return em.createQuery("SELECT pi FROM Picture pi", Picture.class).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(pi) FROM Picture pi", Long.class).getSingleResult();
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
