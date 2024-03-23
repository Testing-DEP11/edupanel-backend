package lk.ijse.dep11.edupanel.repository.custom.impl;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.Picture;
import lk.ijse.dep11.edupanel.repository.CrudRepositoryImpl;
import lk.ijse.dep11.edupanel.repository.custom.PictureRepository;

import java.util.Optional;

public class PictureRepositoryImpl extends CrudRepositoryImpl<Picture, Lecturer> implements PictureRepository {
    @Override
    public void deleteById(Lecturer pk) {
        getEntityManager().remove(getEntityManager().find(Picture.class, pk.getId()));
    }

    @Override
    public Optional<Picture> findById(Lecturer pk) {
        return Optional.ofNullable(getEntityManager().find(Picture.class, pk.getId()));
    }
}
