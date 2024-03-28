package lk.ijse.dep11.edupanel.service.customer.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.entity.Picture;
import lk.ijse.dep11.edupanel.exception.AppException;
import lk.ijse.dep11.edupanel.repository.LecturerRepository;
import lk.ijse.dep11.edupanel.repository.LinkedInRepository;
import lk.ijse.dep11.edupanel.repository.PictureRepository;
import lk.ijse.dep11.edupanel.service.customer.LecturerService;
import lk.ijse.dep11.edupanel.service.util.Transformer;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LinkedInRepository linkedInRepository;
    private final PictureRepository pictureRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    public LecturerServiceImpl(LecturerRepository lecturerRepository, LinkedInRepository linkedInRepository, PictureRepository pictureRepository, Transformer transformer, Bucket bucket) {
        this.lecturerRepository = lecturerRepository;
        this.linkedInRepository = linkedInRepository;
        this.pictureRepository = pictureRepository;
        this.transformer = transformer;
        this.bucket = bucket;
    }

    @Override
    public LecturerTO saveLecturer(LecturerReqTO lecturerReqTO) {

        Lecturer lecturer = transformer.fromLecturerReqTO(lecturerReqTO);
        lecturerRepository.save(lecturer);

        if(lecturerReqTO.getLinkedIn() != null) {
            linkedInRepository.save(lecturer.getLinkedIn());
        }

        String signUrl = null;
        if(lecturerReqTO.getPicture() != null) {
            Picture picture = new Picture(lecturer, "lecturer/" + lecturer.getId());
            pictureRepository.save(picture);

            Blob blobRef = null;
            try {
                blobRef = bucket.create(picture.getPicturePath(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
            } catch (IOException e) {
                throw new AppException(500, "Failed to upload the image", e);
            }
            signUrl = (blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
        }

        LecturerTO lecturerTO = transformer.toLecturerTO(lecturer);
        lecturerTO.setPicture(signUrl);
        return lecturerTO;
    }

    @Override
    public void updateLecturerDetails(LecturerReqTO lecturerReqTO) {
        Lecturer currentLecturer = lecturerRepository.findById(lecturerReqTO.getId()).orElseThrow(() -> new AppException(404, "No lecturer associated with the id"));

        Blob blobRef = null;
        if (currentLecturer.getPicture() != null) {
            blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
            pictureRepository.delete(currentLecturer.getPicture());
        }

        if (currentLecturer.getLinkedIn() != null) {
            linkedInRepository.delete(currentLecturer.getLinkedIn());
        }

        Lecturer newLecturer = transformer.fromLecturerReqTO(lecturerReqTO);
        newLecturer.setLinkedIn(null);

        newLecturer = lecturerRepository.save(newLecturer);

        /* Let's check whether we have new stuff, if so let's persist them */
        if(lecturerReqTO.getPicture() != null) {
            Picture picture = new Picture(newLecturer, "lecturers/" + newLecturer.getId());
            newLecturer.setPicture(pictureRepository.save(picture));
        }

        if (lecturerReqTO.getLinkedIn() != null) {
            LinkedIn linkedIn = new LinkedIn(newLecturer, lecturerReqTO.getLinkedIn());
            newLecturer.setLinkedIn(linkedInRepository.save(linkedIn));
        }

        try {
            if (lecturerReqTO.getPicture() != null) {
                bucket.create(newLecturer.getPicture().getPicturePath(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
            }else if (blobRef != null){
                blobRef.delete();
            }
        } catch (IOException e) {
            throw new AppException(500, "Failed to update the image", e);
        }
    }

    @Override
    public void updateLecturerDetails(LecturerTO lecturerTO) {

        Lecturer currentLecturer = lecturerRepository.findById(lecturerTO.getId()).orElseThrow(() -> new AppException(404, "No lecturer associated with the id"));

        /* Remove the old linked in */
        if (currentLecturer.getLinkedIn() != null) {
            linkedInRepository.delete(currentLecturer.getLinkedIn());
        }


        Lecturer newLecturer = transformer.fromLecturerTO(lecturerTO);
        newLecturer.setLinkedIn(null);

        newLecturer = lecturerRepository.save(newLecturer);

        /* Add a new linked in entry if exists */
        if (lecturerTO.getLinkedIn() != null) {
            LinkedIn linkedIn = new LinkedIn(newLecturer, lecturerTO.getLinkedIn());
            newLecturer.setLinkedIn(linkedInRepository.save(linkedIn));
        }

    }

    @Override
    public void deleteLecturer(Integer lecturerId) {
        if (!lecturerRepository.existsById(lecturerId)) throw new AppException(404, "No lecturer found");

        lecturerRepository.deleteById(lecturerId);

    }

    @Override
    public LecturerTO getLecturerDetails(Integer lecturerId) {

        Optional<Lecturer> optLecturer = lecturerRepository.findById(lecturerId);
        if (optLecturer.isEmpty()) throw new AppException(404, "No lecturer found");

        LecturerTO lecturerTO = transformer.toLecturerTO(optLecturer.get());

        if(optLecturer.get().getPicture() != null) {
            lecturerTO.setPicture(bucket.get(optLecturer.get().getPicture().getPicturePath()).signUrl(1, TimeUnit.DAYS).toString());
        }
        return lecturerTO;

    }

    @Override
    public List<LecturerTO> getLecturers(LecturerType type) {

        List<Lecturer> lecturerList;
        if (type == LecturerType.FULL_TIME) {
            lecturerList = lecturerRepository.findFullTimeLecturers();
        } else if (type == LecturerType.VISITING) {
            lecturerList = lecturerRepository.findVisitingLecturers();
        } else {
            lecturerList = lecturerRepository.findAll();
        }
        return lecturerList.stream().map(lecturer -> {
            LecturerTO lecturerTO = transformer.toLecturerTO(lecturer);
            if(lecturer.getPicture() != null) {
                lecturerTO.setPicture(bucket.get(lecturer.getPicture().getPicturePath()).signUrl(1, TimeUnit.DAYS).toString());
            }
            return lecturerTO;
        }).collect(Collectors.toList());


    }

}
