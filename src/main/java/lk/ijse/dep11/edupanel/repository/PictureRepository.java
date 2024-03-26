package lk.ijse.dep11.edupanel.repository;

import lk.ijse.dep11.edupanel.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
