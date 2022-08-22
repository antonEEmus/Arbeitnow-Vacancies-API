package core.basesyntax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountVacanciesByLocationDto {
    private String location;
    private Long vacanciesPerLocation;
}
