package lk.ijse.dep11.edupanel.repository.custom.impl;

import lk.ijse.dep11.edupanel.repository.custom.QueryRepository;

import javax.persistence.EntityManager;

public class QueryRepositoryImpl implements QueryRepository {

    private EntityManager em;
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
