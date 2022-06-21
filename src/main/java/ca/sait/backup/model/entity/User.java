package ca.sait.backup.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String password;
    private String email;
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

}
