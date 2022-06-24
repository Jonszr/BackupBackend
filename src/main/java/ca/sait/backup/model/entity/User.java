package ca.sait.backup.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data
@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects;

    private String name;
    private String password;
    private String email;
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

}
