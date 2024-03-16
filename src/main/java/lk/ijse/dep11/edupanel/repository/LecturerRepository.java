package lk.ijse.dep11.edupanel.repository;

import lk.ijse.dep11.edupanel.entity.Lecturer;

import java.util.List;

public interface LecturerRepository {

    Lecturer saveLecturer(Lecturer lecturer);
    void updateLecturer(Lecturer lecturer);
    void deleteLecturerById(int id);
    boolean existsLecturerById(int id);
    Lecturer findLecturerById(int id);
    List<Lecturer> findAllLecturers();
    long countLecturers();
}
