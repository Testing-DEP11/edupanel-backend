package lk.ijse.dep11.edupanel.repository;

import lk.ijse.dep11.edupanel.entity.LinkedIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LinkedInRepository extends JpaRepository<LinkedIn, Integer> {
}
