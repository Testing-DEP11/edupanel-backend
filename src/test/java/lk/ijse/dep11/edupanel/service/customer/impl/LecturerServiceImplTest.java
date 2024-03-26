package lk.ijse.dep11.edupanel.service.customer.impl;

import com.google.cloud.storage.Bucket;
import lk.ijse.dep11.edupanel.WebAppConfig;
import lk.ijse.dep11.edupanel.WebRootConfig;
import lk.ijse.dep11.edupanel.exception.AppException;
import lk.ijse.dep11.edupanel.service.customer.LecturerService;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@SpringJUnitWebConfig(classes = {WebAppConfig.class, WebRootConfig.class})
//@ExtendWith(MockitoExtension.class)
class LecturerServiceImplTest {

    private LecturerService lecturerService;
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private Bucket bucket;
    private EntityManager entityManager;

//    @Mock
//    private LecturerRepository lecturerRepository;
//    @Mock
//    private LinkedInRepository linkedInRepository;
//    @Mock
//    private PictureRepository pictureRepository;

    @BeforeEach
    void setUp() {
//        entityManager = emf.createEntityManager();
//        AppStore.setEntityManager(entityManager);
//        AppStore.setBucket(bucket);
//        lecturerService = ServiceFactory.getInstance().getService(ServiceFactory.ServiceType.LECTURER);

//        when(lecturerRepository.save(any(Lecturer.class))).thenAnswer(inv -> {
//            Lecturer lecturer = inv.getArgument(0);
//            lecturer.setId(1);
//            return lecturer;
//        });
//
//        when(linkedInRepository.save(any(LinkedIn.class))).thenAnswer(inv -> inv.getArgument(0));
//
//        lecturerService.setLecturerRepository(lecturerRepository);
//        lecturerService.setPictureRepository(pictureRepository);
//        lecturerService.setLinkedInRepository(linkedInRepository);
    }

    @AfterEach
    void tearDown() {
//        entityManager.close();
    }

    @Test
    void saveLecturer() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO(
                "Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com"
        );
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTO);
        assertNotNull(lecturerTO.getId());
        assertTrue(lecturerTO.getId() > 0);
        assertEquals(lecturerReqTO.getName(), lecturerTO.getName());
        assertEquals(lecturerReqTO.getDesignation(), lecturerTO.getDesignation());
        assertEquals(lecturerReqTO.getQualifications(), lecturerTO.getQualifications());
        assertEquals(lecturerReqTO.getType(), lecturerTO.getType());
        assertEquals(lecturerReqTO.getDisplayOrder(), lecturerTO.getDisplayOrder());
        assumingThat(lecturerReqTO.getLinkedIn() != null, () -> assertEquals(lecturerReqTO.getLinkedIn(), lecturerTO.getLinkedIn()));
        assumingThat(lecturerReqTO.getLinkedIn() == null, () -> assertNull(lecturerTO.getLinkedIn()));
    }

    @Test
    void deleteLecturer() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO(
                "Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com"
        );
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTO);
        lecturerService.deleteLecturer(lecturerTO.getId());

        assertThrows(AppException.class, () -> lecturerService.getLecturerDetails(lecturerTO.getId()));
        assertThrows(AppException.class, () -> lecturerService.deleteLecturer(-100));
    }

    @Test
    void getLecturerDetails() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO(
                "Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com"
        );
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTO);
        LecturerTO lecturerDetails = lecturerService.getLecturerDetails(lecturerTO.getId());

        assertEquals(lecturerTO, lecturerDetails);
        assertThrows(AppException.class, () -> lecturerService.getLecturerDetails(-100));
    }

    @Test
    void getLecturers() {
        for (int i = 0; i < 10; i++) {
            LecturerReqTO lecturerReqTO = new LecturerReqTO(
                    "Amith",
                    "Associate Lecturer", "BSc, MSc",
                    i < 5 ? LecturerType.VISITING : LecturerType.FULL_TIME, 5,
                    null,
                    "https://linkedin.com"
            );
            lecturerService.saveLecturer(lecturerReqTO);
        }
        assertTrue(lecturerService.getLecturers(null).size() >= 10);
        assertTrue(lecturerService.getLecturers(LecturerType.FULL_TIME).size() >= 5);
        assertTrue(lecturerService.getLecturers(LecturerType.VISITING).size() >= 5);
    }

    @Test
    void updateLecturerDetails() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO(
                "Amith",
                "Associate Lecturer", "BSc, MSc",
                LecturerType.VISITING, 5,
                null,
                "https://linkedin.com"
        );
        LecturerTO lecturerTO = lecturerService.saveLecturer(lecturerReqTO);
        lecturerTO.setName("Nuwan");
        lecturerTO.setLinkedIn(null);
        lecturerService.updateLecturerDetails(lecturerTO);
        LecturerTO lecturer = lecturerService.getLecturerDetails(lecturerTO.getId());
        assertEquals(lecturerTO, lecturer);
    }
}