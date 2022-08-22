package core.basesyntax.dto;

import java.util.List;
import lombok.Data;

@Data
public class ApiResponseDto {
    private List<ApiVacancyDto> data;
    private ApiLinksDto links;
}
