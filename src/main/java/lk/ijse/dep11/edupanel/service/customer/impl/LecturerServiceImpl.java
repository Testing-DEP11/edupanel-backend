package lk.ijse.dep11.edupanel.service.customer.impl;

import lk.ijse.dep11.edupanel.repository.RepositoryFactory;
import lk.ijse.dep11.edupanel.repository.custom.LecturerRepository;
import lk.ijse.dep11.edupanel.repository.custom.LinkedInRepository;
import lk.ijse.dep11.edupanel.repository.custom.PictureRepository;
import lk.ijse.dep11.edupanel.service.customer.LecturerService;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;

import java.util.List;

public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.LECTURER);
    private final LinkedInRepository linkedInRepository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.LINKEDIN);
    private final PictureRepository pictureRepository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.PICTURE);
    @Override
    public LecturerTO saveLecturer(LecturerReqTO lecturerReqTO) {
        return null;
    }

    @Override
    public void updateLecturerDetailsWithImage(LecturerReqTO lecturerReqTO) {

    }

    @Override
    public void updateLecturerDetailsWithoutImage(LecturerTO lecturerTO) {

    }

    @Override
    public void deleteLecturer(Integer lecturerId) {

    }

    @Override
    public LecturerTO getLecturerDetails(Integer lecturerId) {
        return null;
    }

    @Override
    public List<LecturerTO> getLecturers(LecturerType type) {
        return null;
    }
}
