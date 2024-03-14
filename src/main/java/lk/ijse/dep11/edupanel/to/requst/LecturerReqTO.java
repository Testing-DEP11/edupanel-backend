package lk.ijse.dep11.edupanel.to.requst;

import lk.ijse.dep11.edupanel.util.LecturerType;
import lk.ijse.dep11.edupanel.validation.LecturerImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerReqTO implements Serializable {

    @NotBlank(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z ]{2,}$", message = "Invalid name")
    private String name;
    @NotBlank(message = "Designation cannot be empty")
    @Length(min = 3, message = "Invalid designation")
    private String designation;
    @NotBlank(message = "Qualification cannot be empty")
    @Length(min = 3, message = "Invalid qualification")
    private String qualifications;
    @NotNull(message = "Invalid type or type is empty")
    private LecturerType type;
    @Null(groups = Create.class, message = "Display order should be empty")
    @NotNull(groups = Update.class, message = "Display order cannot be empty")
    @PositiveOrZero(groups = Update.class, message = "Invalid display order")
    private Integer displayOrder;
    @LecturerImage
    private MultipartFile picture;
    @Pattern(regexp = "^http(s)://.+$", message = "Invalid linkedin")
    private String linkedIn;

    public interface Create extends Default{}
    public interface Update extends Default{}
}
