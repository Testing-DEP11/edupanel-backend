package lk.ijse.dep11.edupanel.entity;

import lk.ijse.dep11.edupanel.util.LecturerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lecturer")
public class Lecturer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 300)
    private String name;
    @Column(nullable = false, length = 600)
    private String designation;
    @Column(nullable = false, length = 600)
    private String qualifications;
    @Column(nullable = false, columnDefinition = "ENUM('FULL_TIME', 'VISITING')")
    @Enumerated(EnumType.STRING)
    private LecturerType type;
    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    public Lecturer(String name, String designation, String qualifications, LecturerType type, int displayOrder) {
        this.name = name;
        this.designation = designation;
        this.qualifications = qualifications;
        this.type = type;
        this.displayOrder = displayOrder;
    }
}
