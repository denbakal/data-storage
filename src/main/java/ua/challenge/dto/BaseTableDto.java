package ua.challenge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BaseTableDto {
    private Long id;
    private Long versionTable;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
