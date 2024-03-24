package lk.ijse.dep11.edupanel.service.util;

import lk.ijse.dep11.edupanel.WebAppConfig;
import lk.ijse.dep11.edupanel.WebRootConfig;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitWebConfig(classes = {WebRootConfig.class, WebAppConfig.class})
class TransformerTest {

    private final Transformer transformer = new Transformer();
    @Test
    void toLecturerTO() {
        Lecturer lecturer = new Lecturer(
                2,
                "Nuwan",
                "Trainee",
                "BSc",
                LecturerType.VISITING,
                5
        );
        lecturer.setLinkedIn(new LinkedIn(lecturer, "https://linkedin.com/nuwan"));
        LecturerTO lecturerTO = transformer.toLecturerTO(lecturer);

        assertEquals(lecturer.getId(), lecturerTO.getId());
        assertEquals(lecturer.getName(), lecturerTO.getName());
        assertEquals(lecturer.getDesignation(), lecturerTO.getDesignation());
        assertEquals(lecturer.getQualifications(), lecturerTO.getQualifications());
        assertEquals(lecturer.getType(), lecturerTO.getType());
        assertEquals(lecturer.getLinkedIn().getUrl(), lecturerTO.getLinkedIn());
    }

    @Test
    void fromLecturerTO() {
        LecturerTO lecturerTO = new LecturerTO(5,
                "Nuwan",
                "Senior Trainer",
                "BSc in Computing",
                LecturerType.FULL_TIME,
                6,
                null, "https://linked.in/nuwan-kasun");

        Lecturer lecturer = transformer.fromLecturerTO(lecturerTO);
        assertEquals(lecturerTO.getId(), lecturer.getId());
        assertEquals(lecturerTO.getName(), lecturer.getName());
        assertEquals(lecturerTO.getDesignation(), lecturer.getDesignation());
        assertEquals(lecturerTO.getQualifications(), lecturer.getQualifications());
        assertEquals(lecturerTO.getType(), lecturer.getType());
        assertEquals(lecturerTO.getDisplayOrder(), lecturer.getDisplayOrder());
        assertEquals(lecturerTO.getLinkedIn(), lecturer.getLinkedIn().getUrl());
    }

    @Test
    void fromLecturerReqTO() {
        LecturerReqTO lecturerReqTO = new LecturerReqTO("Kasun",
                "Senior Trainer",
                "BSc in Computing",
                LecturerType.FULL_TIME,
                10,
                null,
                "http://linkedin.com/kasun");
        Lecturer lecturer = transformer.fromLecturerReqTO(lecturerReqTO);

        assertEquals(lecturerReqTO.getName(), lecturer.getName());
        assertEquals(lecturerReqTO.getDesignation(), lecturer.getDesignation());
        assertEquals(lecturerReqTO.getQualifications(), lecturer.getQualifications());
        assertEquals(lecturerReqTO.getType(), lecturer.getType());
        assertEquals(lecturerReqTO.getDisplayOrder(), lecturer.getDisplayOrder());
        assertEquals(lecturerReqTO.getLinkedIn(), lecturer.getLinkedIn().getUrl());
    }
}