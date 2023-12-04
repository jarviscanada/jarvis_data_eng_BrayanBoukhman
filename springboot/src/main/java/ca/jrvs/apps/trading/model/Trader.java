package ca.jrvs.apps.trading.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
@Data
@Entity
public class Trader {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "country")
    private String country;
    @Column(name = "email")
    private String email;
}
