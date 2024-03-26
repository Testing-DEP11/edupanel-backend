package lk.ijse.dep11.edupanel.repository.impl;

import lk.ijse.dep11.edupanel.repository.QueryRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class QueryRepositoryImpl implements QueryRepository {

    @PersistenceContext
    private EntityManager em;

}
