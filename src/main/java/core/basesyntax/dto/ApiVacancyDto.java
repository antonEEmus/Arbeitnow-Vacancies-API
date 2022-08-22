package core.basesyntax.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiVacancyDto {
    private String slug;
    @JsonProperty("company_name")
    private String companyName;
    private String title;
    private String description;
    private Boolean remote;
    private String url;
    private String location;
    @JsonProperty("created_at")
    private Long createdAt;
}
