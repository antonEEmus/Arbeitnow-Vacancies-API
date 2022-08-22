package core.basesyntax.component.mapper.impl;

import core.basesyntax.component.mapper.DtoToModelMapper;
import core.basesyntax.dto.ApiVacancyDto;
import core.basesyntax.model.Vacancy;
import org.springframework.stereotype.Component;

@Component
public class ApiVacancyMapper implements DtoToModelMapper<ApiVacancyDto, Vacancy> {
    @Override
    public Vacancy toModel(ApiVacancyDto dto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setSlug(dto.getSlug());
        vacancy.setTitle(dto.getTitle());
        vacancy.setCompanyName(dto.getCompanyName());
        vacancy.setDescription(dto.getDescription());
        vacancy.setLocation(dto.getLocation());
        vacancy.setUrl(dto.getUrl());
        vacancy.setRemote(dto.getRemote());
        vacancy.setCreatedAt(dto.getCreatedAt());
        return vacancy;
    }
}
