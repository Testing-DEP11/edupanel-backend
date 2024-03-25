package lk.ijse.dep11.edupanel.api;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.entity.Picture;
import lk.ijse.dep11.edupanel.service.ServiceFactory;
import lk.ijse.dep11.edupanel.service.customer.LecturerService;
import lk.ijse.dep11.edupanel.store.AppStore;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import lk.ijse.dep11.edupanel.util.LecturerType;
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
    private Bucket bucket;

    private LecturerService lecturerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data", produces = "application/json")
    public LecturerTO createNewLecturer(@ModelAttribute @Validated(LecturerReqTO.Create.class) LecturerReqTO lecturerReqTO){

        return lecturerService.saveLecturer(lecturerReqTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "multipart/form-data")
    public void updateLecturerDetailsViaMultipart(@PathVariable("lecturer-id") Integer lecturerId,
                                                  @ModelAttribute @Validated(LecturerReqTO.Update.class) LecturerReqTO lecturerReqTO){
        lecturerReqTO.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerReqTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{lecturer-id}", consumes = "application/json")
    public void updateLecturerDetailsViaJson(@PathVariable("lecturer-id") Integer lecturerId, @RequestBody @Validated LecturerTO lecturerTO){
        lecturerTO.setId(lecturerId);
        lecturerService.updateLecturerDetails(lecturerTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping({"/{lecturer-id}"})
    public void deleteLecturer(@PathVariable("lecturer-id") Integer lecturerId){
        lecturerService.deleteLecturer(lecturerId);
    }

    @GetMapping(produces = "application/json")
    public List<LecturerTO> getAllLecturers(){
        return lecturerService.getLecturers(null);
    }

    @GetMapping(value = "/{lecturer-id}", produces = "application/json")
    public LecturerTO getLecturerDetails(@PathVariable("lecturer-id") Integer lectureId){
        return lecturerService.getLecturerDetails(lectureId);
    }

    @GetMapping(params = "type=full-time", produces = "application/json")
    public List<LecturerTO> getFullTimeLecturers(){
        return lecturerService.getLecturers(LecturerType.FULL_TIME);
    }

    @GetMapping(params = "type=visiting", produces = "application/json")
    public List<LecturerTO> getVisitingLecturers(){
        return lecturerService.getLecturers(LecturerType.VISITING);
    }
}
