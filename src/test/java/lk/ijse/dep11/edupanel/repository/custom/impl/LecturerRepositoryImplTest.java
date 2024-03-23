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

import java.util.List;
import java.util.Optional;

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
        entityManager.getTransaction().commit();
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
        Lecturer lecturer = new Lecturer("Lakith Rathnayake",
                "Software Engineer",
                "BSc Eng(Hons)",
                LecturerType.FULL_TIME,
                0);
        Lecturer savedLecturer = repository.save(lecturer);
        savedLecturer.setName("Kasun Sampath");
        savedLecturer.setQualifications("BSc in computing");
        savedLecturer.setType(LecturerType.VISITING);
        repository.update(savedLecturer);

        Lecturer updatedLecturer = entityManager.find(Lecturer.class, savedLecturer.getId());
        assertEquals(savedLecturer, updatedLecturer);
    }

    @Test
    void deleteById() {
        Lecturer lecturer = new Lecturer("Lakith Rathnayake",
                "Software Engineer",
                "BSc Eng(Hons)",
                LecturerType.FULL_TIME,
                0);
        Lecturer savedLecturer = repository.save(lecturer);
        repository.deleteById(savedLecturer.getId());

        Lecturer dbLecturer = entityManager.find(Lecturer.class, savedLecturer.getId());
        assertNull(dbLecturer);
    }

    @Test
    void existsById() {
        Lecturer lecturer = new Lecturer("Lakith Rathnayake",
                "Software Engineer",
                "BSc Eng(Hons)",
                LecturerType.FULL_TIME,
                0);
        Lecturer savedLecturer = repository.save(lecturer);
        boolean exists = repository.existsById(savedLecturer.getId());
        assertTrue(exists);

    }

    @Test
    void findById() {
        Lecturer lecturer = new Lecturer("Lakith Rathnayake",
                "Software Engineer",
                "BSc Eng(Hons)",
                LecturerType.FULL_TIME,
                0);
        Lecturer savedLecturer = repository.save(lecturer);
        Optional<Lecturer> lecturer1 = repository.findById(savedLecturer.getId());
        Optional<Lecturer> lecturer2 = repository.findById(1000);

        assertTrue(lecturer1.isPresent());
        assertTrue(lecturer2.isEmpty());
    }

    @Test
    void findAll() {
        for (int i = 0; i < 8; i++) {
            Lecturer lecturer = new Lecturer("Kasun Sampath",
                    "Senior Trainer",
                    "BSc (Hons) in Computing",
                    LecturerType.FULL_TIME,
                    0);
            repository.save(lecturer);
        }
        List<Lecturer> lecturerList = repository.findAll();

        assertEquals(8, lecturerList.size());
    }

    @Test
    void count() {
        for (int i = 0; i < 80; i++) {
            Lecturer lecturer = new Lecturer("Kasun Sampath",
                    "Senior Trainer",
                    "BSc (Hons) in Computing",
                    LecturerType.FULL_TIME,
                    0);
            repository.save(lecturer);
        }
        long count = repository.count();

        assertEquals(80, count);
    }
}