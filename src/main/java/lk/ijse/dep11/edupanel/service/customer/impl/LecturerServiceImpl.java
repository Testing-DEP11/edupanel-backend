package lk.ijse.dep11.edupanel.service.customer.impl;

import lk.ijse.dep11.edupanel.repository.RepositoryFactory;
import lk.ijse.dep11.edupanel.repository.custom.LecturerRepository;
import lk.ijse.dep11.edupanel.repository.custom.LinkedInRepository;
import lk.ijse.dep11.edupanel.repository.custom.PictureRepository;
import lk.ijse.dep11.edupanel.service.customer.LecturerService;
import lk.ijse.dep11.edupanel.store.AppStore;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;

import java.util.List;

public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.LECTURER);
    private final LinkedInRepository linkedInRepository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.LINKEDIN);
    private final PictureRepository pictureRepository = RepositoryFactory.getInstance().getRepository(RepositoryFactory.RepositoryType.PICTURE);

    public LecturerServiceImpl() {
        lecturerRepository.setEntityManager(AppStore.getEntityManager());
        linkedInRepository.setEntityManager(AppStore.getEntityManager());
        pictureRepository.setEntityManager(AppStore.getEntityManager());
    }

    @Override
    public LecturerTO saveLecturer(LecturerReqTO lecturerReqTO) {
        AppStore.getEntityManager().getTransaction().begin();
        try {
            AppStore.getEntityManager().getTransaction().commit();
        } catch (Throwable e) {
            AppStore.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void updateLecturerDetailsWithImage(LecturerReqTO lecturerReqTO) {
        AppStore.getEntityManager().getTransaction().begin();
        try {
            AppStore.getEntityManager().getTransaction().commit();
        } catch (Throwable e) {
            AppStore.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void updateLecturerDetailsWithoutImage(LecturerTO lecturerTO) {
        AppStore.getEntityManager().getTransaction().begin();
        try {
            AppStore.getEntityManager().getTransaction().commit();
        } catch (Throwable e) {
            AppStore.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void deleteLecturer(Integer lecturerId) {
        AppStore.getEntityManager().getTransaction().begin();
        try {
            AppStore.getEntityManager().getTransaction().commit();
        } catch (Throwable e) {
            AppStore.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public LecturerTO getLecturerDetails(Integer lecturerId) {
        AppStore.getEntityManager().getTransaction().begin();
        try {
            AppStore.getEntityManager().getTransaction().commit();
        } catch (Throwable e) {
            AppStore.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<LecturerTO> getLecturers(LecturerType type) {
        AppStore.getEntityManager().getTransaction().begin();
        try {
            AppStore.getEntityManager().getTransaction().commit();
        } catch (Throwable e) {
            AppStore.getEntityManager().getTransaction().rollback();
            throw e;
        }
    }
}
