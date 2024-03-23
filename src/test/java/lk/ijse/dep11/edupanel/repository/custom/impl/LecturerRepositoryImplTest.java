package lk.ijse.dep11.edupanel.repository.custom.impl;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.repository.RepositoryFactory;
import lk.ijse.dep11.edupanel.repository.custom.LecturerRepository;
import lk.ijse.dep11.edupanel.util.LecturerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class LecturerRepositoryImplTest {

    private final LecturerRepository repository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.LECTURER);
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
        repository.setEntityManager(entityManager);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
        entityManager.close();
    }

    @Test
    void save() {
        Lecturer lecturer = new Lecturer("Lakith Rathnayake",
                "Software Engineer",
                "BSc Eng(Hons)",
                LecturerType.FULL_TIME,
                0);
        Lecturer savedLecturer = repository.save(lecturer);
        assertTrue(savedLecturer.getId() > 0);
        savedLecturer = entityManager.find(Lecturer.class, savedLecturer.getId());
        assertNotNull(savedLecturer);
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void existsById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void count() {
    }
}