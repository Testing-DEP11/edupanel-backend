package lk.ijse.dep11.edupanel.service.util;

import lk.ijse.dep11.edupanel.entity.Lecturer;
import lk.ijse.dep11.edupanel.entity.LinkedIn;
import lk.ijse.dep11.edupanel.to.LecturerTO;
import lk.ijse.dep11.edupanel.to.requst.LecturerReqTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Transformer {

    private final ModelMapper mapper = new ModelMapper();

    public Transformer() {
        mapper.typeMap(LinkedIn.class, String.class)
                .setConverter(ctx -> ctx.getSource().getUrl());
        mapper.typeMap(String.class, LinkedIn.class)
                .setConverter(ctx -> new LinkedIn(null, ctx.getSource()));
    }

    Lecturer fromLecturerReqTO(LecturerReqTO lecturerReqTO) {
        Lecturer lecturer = mapper.map(lecturerReqTO, Lecturer.class);
        if(lecturer.getLinkedIn() == null) {
            lecturer.setLinkedIn(null);
        } else {
            lecturer.getLinkedIn().setLecturer(lecturer);
        }
        if (lecturerReqTO.getPicture() == null || lecturerReqTO.getPicture().isEmpty())
            lecturer.setPicture(null);
        return lecturer;
    }
    
    Lecturer fromLecturerTO(LecturerTO lecturerTO) {
//        return mapper.map(lecturerTO, Lecturer.class);
        Lecturer lecturer = mapper.map(lecturerTO, Lecturer.class);
        lecturer.getLinkedIn().setLecturer(lecturer);
        return lecturer;
    }
    LecturerTO toLecturerTO(Lecturer lecturer) {
        return mapper.map(lecturer, LecturerTO.class);
    }

    List<LecturerTO> toLecturerTOList(List<Lecturer> lecturerList) {
//        return lecturerList.stream().map(lecturer -> {
//            return this.toLecturerTO(lecturer);
//        }).collect(Collectors.toList());
        return lecturerList.stream().map(this::toLecturerTO).collect(Collectors.toList());
    }
}
