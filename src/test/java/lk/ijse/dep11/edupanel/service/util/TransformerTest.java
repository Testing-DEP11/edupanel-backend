package lk.ijse.dep11.edupanel.service.util;

import lk.ijse.dep11.edupanel.WebAppConfig;
import lk.ijse.dep11.edupanel.WebRootConfig;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.to.LecturerTO;
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
}