package lk.ijse.dep11.edupanel.to;

import lk.ijse.dep11.edupanel.util.LecturerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerTO implements Serializable {
    @Null(message = "Id should be empty")
    private Integer id;
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
    @NotNull(message = "Display order cannot be empty")
    @PositiveOrZero(message = "Invalid display order")
    private Integer displayOrder;
    @Null(message = "Picture should be empty")
    private String picture;
    @Pattern(regexp = "^http(s)://.+$", message = "Invalid linkedin")
    private String linkedIn;
}
