package ua.challenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BaseFieldDto {
    private Long id;
    private long baseTableId;
    private int ordinal;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int isActive;
    private int isDeleted;
}
