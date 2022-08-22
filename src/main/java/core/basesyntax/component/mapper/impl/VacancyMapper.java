package core.basesyntax.component.mapper.impl;

import core.basesyntax.component.mapper.ModelToDtoMapper;
import core.basesyntax.dto.VacancyResponseDto;
import core.basesyntax.model.Vacancy;
import org.springframework.stereotype.Component;

@Component
public class VacancyMapper implements ModelToDtoMapper<VacancyResponseDto, Vacancy> {
    @Override
    public VacancyResponseDto toDto(Vacancy vacancy) {
        VacancyResponseDto dto = new VacancyResponseDto();
        dto.setId(vacancy.getId());
        dto.setSlug(vacancy.getSlug());
        dto.setTitle(vacancy.getTitle());
        dto.setCompanyName(vacancy.getCompanyName());
        dto.setDescription(vacancy.getDescription());
        dto.setLocation(vacancy.getLocation());
        dto.setUrl(vacancy.getUrl());
        dto.setRemote(vacancy.getRemote());
        dto.setCreatedAt(vacancy.getCreatedAt());
        return dto;
    }
}
