package lk.ijse.dep11.edupanel.repository.custom;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LinkedInRepository extends CrudRepository<LinkedIn, Lecturer> {
}
