package ua.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Date dateOfBirth;
    private String gender;
    private Integer children;

    @OneToOne(cascade = CascadeType.ALL)
    private Marketing marketing;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
