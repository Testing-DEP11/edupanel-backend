package lk.ijse.dep11.edupanel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "picture")
public class Picture implements SuperEntity {
    @Id
    @Column(name = "lecturer_id")
    private Integer lecturerId;
    @MapsId
    @OneToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    private Lecturer lecturer;
    @Column(name = "picture_path", nullable = false, length = 400)
    private String picturePath;

    public Picture(Lecturer lecturer, String picturePath) {
        this.lecturer = lecturer;
        this.picturePath = picturePath;
    }
}
