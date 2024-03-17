package lk.ijse.dep11.edupanel.repository;

import lk.ijse.dep11.edupanel.repository.custom.impl.LecturerRepositoryImpl;
import lk.ijse.dep11.edupanel.repository.custom.impl.LinkedInRepositoryImpl;
import lk.ijse.dep11.edupanel.repository.custom.impl.PictureRepositoryImpl;
import lk.ijse.dep11.edupanel.repository.custom.impl.QueryRepositoryImpl;

public class RepositoryFactory {

    public enum RepositoryType{
        LECTURER, LINKEDIN, PICTURE, QUERY
    }

    private RepositoryFactory(){

    }
    private static RepositoryFactory INSTANCE;
    public static RepositoryFactory getInstance(){
        return INSTANCE == null ? INSTANCE = new RepositoryFactory() : INSTANCE;
    }

    public <T extends SuperRepository> T getRepository(RepositoryType type) {
        switch (type) {
            case LECTURER:
                return (T) new LecturerRepositoryImpl();
            case LINKEDIN:
                return (T) new LinkedInRepositoryImpl();
            case PICTURE:
                return (T) new PictureRepositoryImpl();
            case QUERY:
                return (T) new QueryRepositoryImpl();
            default:
                throw new IllegalArgumentException();
        }
    }
}
