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
@Table(name = "base_lane")
public class BaseLane {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_lane_id_seq")
    @SequenceGenerator(name = "base_lane_id_seq", sequenceName = "base_lane_id_seq")
    private Long id;

    @Column(name = "base_table_id")
    private long baseTableId;

    @Column
    private int ordinal;

    @CreationTimestamp
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @CreationTimestamp
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "is_delete")
    private int isDeleted;
}
