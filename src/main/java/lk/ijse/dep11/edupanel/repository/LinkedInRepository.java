package lk.ijse.dep11.edupanel.repository;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;

import java.util.List;

public interface LinkedInRepository {
    LinkedIn saveLinkedIn(LinkedIn linkedIn);
    void updateLinkedIn(LinkedIn linkedIn);
    void deleteLinkedInByLecturer(Lecturer lecturer);
    boolean existsLinkedInByLecturer(Lecturer lecturer);
    LinkedIn findLinkedInByLecturer(Lecturer lecturer);
    List<LinkedIn> findAllLinkedIns();
    long countLinkedIns();

}
