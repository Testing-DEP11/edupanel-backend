package lk.ijse.dep11.edupanel.repository.impl;

import lk.ijse.dep11.edupanel.AppInitializer;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.repository.LecturerRepository;
import lk.ijse.dep11.edupanel.util.LecturerType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {WebRootConfig.class})
//@ContextConfiguration(classes = {WebAppConfig.class, WebRootConfig.class})
//@WebAppConfiguration
@SpringBootTest
@Transactional
class LecturerRepositoryImplTest {

    @Autowired
    private LecturerRepository repository;
    @PersistenceContext
    private EntityManager entityManager;


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
        repository.save(savedLecturer);

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

//        assertEquals(80, count);
        assertTrue(count >= 80);
    }
}