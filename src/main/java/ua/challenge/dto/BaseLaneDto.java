package ua.challenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BaseLaneDto {
    private Long id;
    private long baseTableId;
    private int ordinal;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int isActive;
    private int isDeleted;
}
