package ua.challenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BaseFieldValueDto {
    private Long id;
    private long baseTableId;
    private long laneId;
    private long fieldId;
    private String value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int isActive;
    private int isDeleted;
}
