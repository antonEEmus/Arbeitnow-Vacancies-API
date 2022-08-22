package core.basesyntax.repository;

import core.basesyntax.dto.CountVacanciesByLocationDto;
import core.basesyntax.model.Vacancy;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    @Query("FROM Vacancy")
    List<Vacancy> findAllPageable(PageRequest pageRequest);

    @Query("FROM Vacancy v ORDER BY v.createdAt DESC")
    List<Vacancy> findTopByCreatedAtOrderByCreatedAtDesc(PageRequest pageRequest);

    @Query("SELECT new core.basesyntax.dto.CountVacanciesByLocationDto(v.location, "
            + "COUNT(v.location)) FROM Vacancy v GROUP BY v.location")
    List<CountVacanciesByLocationDto> countAllByLocation();
}
