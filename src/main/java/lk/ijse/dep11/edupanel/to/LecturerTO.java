package lk.ijse.dep11.edupanel.to;

import lk.ijse.dep11.edupanel.util.LecturerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerTO implements Serializable {
    private Integer id;
    private String name;
    private String designation;
    private String qualifications;
    private LecturerType type;
    private String picture;
    private String linkedin;
}
