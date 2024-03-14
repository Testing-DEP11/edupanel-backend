package lk.ijse.dep11.edupanel.api;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.entity.Picture;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
public class LecturerHttpController {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Bucket bucket;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    public LecturerTO createNewLecturer(@ModelAttribute @Validated(LecturerReqTO.Create.class) LecturerReqTO lecturerReqTO){
        em.getTransaction().begin();
        try {
            Lecturer lecturer = mapper.map(lecturerReqTO, Lecturer.class);
            lecturer.setPicture(null);
            lecturer.setLinkedIn(null);
            em.persist(lecturer);
            LecturerTO lecturerTO = mapper.map(lecturer, LecturerTO.class);

            if(lecturerReqTO.getLinkedIn() != null) {
                em.persist(new LinkedIn(lecturer, lecturerReqTO.getLinkedIn()));
                lecturerTO.setLinkedIn(lecturerReqTO.getLinkedIn());
            }

            if(lecturerReqTO.getPicture() != null) {
                Picture picture = new Picture(lecturer, "lecturer/" + lecturer.getId());
                em.persist(picture);

                Blob blobRef = bucket.create(picture.getPicturePath(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
                lecturerTO.setPicture(blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());

            }

            em.getTransaction().commit();

            return lecturerTO;
        } catch (Throwable t) {
            em.getTransaction().rollback();
            throw new RuntimeException(t);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerDetailsViaMultipart(@PathVariable("lecturer-id") Integer lecturerId,
                                                  @ModelAttribute @Validated(LecturerReqTO.Update.class) LecturerReqTO lecturerReqTO){
        Lecturer currentLecturer = em.find(Lecturer.class, lecturerId);
        if(currentLecturer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        em.getTransaction().begin();
        try {
            Lecturer newLecturer = mapper.map(lecturerReqTO, Lecturer.class);
            newLecturer.setId(lecturerId);
            newLecturer.setPicture(null);
            newLecturer.setLinkedIn(null);

            if(lecturerReqTO.getPicture() != null) {
                newLecturer.setPicture(new Picture(newLecturer, "lecturer/" + lecturerId));
            }

            if (lecturerReqTO.getLinkedIn() != null) {
                newLecturer.setLinkedIn(new LinkedIn(newLecturer, lecturerReqTO.getLinkedIn()));
            }

            updateLinkedIn(currentLecturer, newLecturer);


            if (newLecturer.getPicture() != null && currentLecturer.getPicture() == null) {
                em.persist(newLecturer.getPicture());
                bucket.create(newLecturer.getPicture().getPicturePath(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
            } else if (newLecturer.getPicture() == null && currentLecturer.getPicture() != null) {
                em.remove(currentLecturer.getPicture());
                bucket.get(currentLecturer.getPicture().getPicturePath()).delete();
            } else if (newLecturer.getPicture() != null){
                em.merge(newLecturer.getPicture());
                bucket.create(newLecturer.getPicture().getPicturePath(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
            }

            em.merge(newLecturer);

            em.getTransaction().commit();
        } catch (Throwable e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerDetailsViaJson(@PathVariable("lecturer-id") Integer lecturerId, @RequestBody @Validated LecturerTO lecturerTO){
        Lecturer currentLecturer = em.find(Lecturer.class, lecturerId);
        if(currentLecturer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        em.getTransaction().begin();
        try {
            Lecturer newLecturer = mapper.map(lecturerTO, Lecturer.class);
            newLecturer.setId(lecturerId);
            newLecturer.setPicture(currentLecturer.getPicture());
            newLecturer.setLinkedIn(lecturerTO.getLinkedIn() != null ? new LinkedIn(newLecturer, lecturerTO.getLinkedIn()): null);

            updateLinkedIn(currentLecturer, newLecturer);

            em.merge(newLecturer);

            em.getTransaction().commit();
        } catch (Throwable e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping({"/{lecturer-id}"})
    public void deleteLecturer(@PathVariable("lecturer-id") Integer lecturerId){
        Lecturer lecturer = em.find(Lecturer.class, lecturerId);
        if(lecturer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        em.getTransaction().begin();
        try {
            em.remove(lecturer);
            if(lecturer.getPicture() != null) {
                bucket.get(lecturer.getPicture().getPicturePath()).delete();
            }

            em.getTransaction().commit();
        } catch (Throwable e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @GetMapping(produces = "application/json")
    public List<LecturerTO> getAllLecturers(){
        TypedQuery<Lecturer> query = em.createQuery("SELECT l FROM Lecturer l", Lecturer.class);
        return getLecturerTOList(query);
    }

    @GetMapping(value = "/{lecturer-id}", produces = "application/json")
    public LecturerTO getLecturerDetails(@PathVariable("lecturer-id") Integer lectureId){
        Lecturer lecturer = em.find(Lecturer.class, lectureId);
        if(lecturer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        LecturerTO lecturerTO = mapper.map(lecturer, LecturerTO.class);
        if(lecturer.getLinkedIn() != null){
            lecturerTO.setLinkedIn(lecturer.getLinkedIn().getUrl());
        }
        if(lecturer.getPicture() != null) {
            lecturerTO.setPicture(bucket.get(lecturer.getPicture().getPicturePath()).signUrl(1, TimeUnit.DAYS).toString());
        }
        return lecturerTO;
    }

    @GetMapping(params = "type=full-time", produces = "application/json")
    public List<LecturerTO> getFullTimeLecturers(){
        TypedQuery<Lecturer> query = em.createQuery("SELECT l FROM Lecturer l WHERE l.type = lk.ijse.dep11.edupanel.util.LecturerType.FULL_TIME", Lecturer.class);
        return getLecturerTOList(query);
    }

    @GetMapping(params = "type=visiting", produces = "application/json")
    public List<LecturerTO> getVisitingLecturers(){
        TypedQuery<Lecturer> query = em.createQuery("SELECT l FROM Lecturer l WHERE l.type = lk.ijse.dep11.edupanel.util.LecturerType.VISITING", Lecturer.class);
        return getLecturerTOList(query);
    }

    private List<LecturerTO> getLecturerTOList(TypedQuery<Lecturer> query) {
        return query.getResultStream().map(lecturerEntity -> {
            LecturerTO lecturerTO = mapper.map(lecturerEntity, LecturerTO.class);
            if(lecturerEntity.getLinkedIn() != null){
                lecturerTO.setLinkedIn(lecturerEntity.getLinkedIn().getUrl());
            }
            if(lecturerEntity.getPicture() != null) {
                lecturerTO.setPicture(bucket.get(lecturerEntity.getPicture().getPicturePath()).signUrl(1, TimeUnit.DAYS).toString());
            }
            return lecturerTO;
        }).collect(Collectors.toList());
    }

    private void updateLinkedIn(Lecturer currentLecturer, Lecturer newLecturer) {
        if (newLecturer.getLinkedIn() != null && currentLecturer.getLinkedIn() == null) {
            em.persist(newLecturer.getLinkedIn());
        } else if (newLecturer.getLinkedIn() == null && currentLecturer.getLinkedIn() != null) {
            em.remove(currentLecturer.getLinkedIn());
        } else if (newLecturer.getLinkedIn() != null){
            em.merge(newLecturer.getLinkedIn());
        }
    }

}
