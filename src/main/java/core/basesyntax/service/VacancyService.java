package core.basesyntax.service;

import core.basesyntax.dto.CountVacanciesByLocationDto;
import core.basesyntax.model.Vacancy;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface VacancyService {
    List<Vacancy> findAll(PageRequest pageRequest);

    List<Vacancy> findTopByCreatedAt(PageRequest pageRequest);

    List<CountVacanciesByLocationDto> countVacanciesByLocation();

    void syncExternalVacancies();
}
