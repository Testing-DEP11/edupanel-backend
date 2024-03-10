package lk.ijse.dep11.edupanel.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lecturers")
@CrossOrigin
public class LecturerHttpController {

    @PostMapping
    public void createNewLecturer(){}

    @PatchMapping("/{lecturer-id}")
    public void updateLecturerDetails(@PathVariable("lecturer-id") Integer lecturerId){}

    @DeleteMapping
    public void deleteLecturer(){}

    @GetMapping
    public void getAllLecturers(){}

    @GetMapping
    public void getFullTimeLecturers(){}

    @GetMapping
    public void getVisitingLecturers(){}

}
