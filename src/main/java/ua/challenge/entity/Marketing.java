package ua.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Marketing {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer cars;
    private Integer shoes;
    private Integer toys;
    private Integer fashion;
    private Integer music;
    private Integer garden;
    private Integer electronic;
    private Integer hifi;
    private Integer food;
}
