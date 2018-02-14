package ua.challenge.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "base_table")
public class BaseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_table_id_seq")
    @SequenceGenerator(name = "base_table_id_seq", sequenceName = "base_table_id_seq")
    private Long id;

    @Column(name = "version_table")
    private Long versionTable;

    @CreationTimestamp
    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @CreationTimestamp
    @Column(name = "valid_to")
    private LocalDateTime validTO;
}
