package lk.ijse.dep11.edupanel.repository.custom.impl;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.repository.CrudRepositoryImpl;
import lk.ijse.dep11.edupanel.repository.custom.LecturerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LecturerRepositoryImpl extends CrudRepositoryImpl<Lecturer, Integer> implements LecturerRepository {
    @Override
    public List<Lecturer> findFullTimeLecturers() {
        return getEntityManager().createQuery("SELECT l FROM Lecturer l WHERE l.type = lk.ijse.dep11.edupanel.util.LecturerType.FULL_TIME", Lecturer.class).getResultList();
    }

    @Override
    public List<Lecturer> findVisitingLecturers() {
        return getEntityManager().createQuery("SELECT l FROM Lecturer l WHERE l.type = lk.ijse.dep11.edupanel.util.LecturerType.VISITING", Lecturer.class).getResultList();
    }
}
