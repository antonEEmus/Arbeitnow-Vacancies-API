package core.basesyntax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyResponseDto {
    private Long id;
    private String slug;
    private String companyName;
    private String title;
    private String description;
    private Boolean remote;
    private String url;
    private String location;
    private Long createdAt;
}
