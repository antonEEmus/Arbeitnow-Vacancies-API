package core.basesyntax.repository;

import core.basesyntax.dto.CountVacanciesByLocationDto;
import core.basesyntax.model.Vacancy;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VacancyRepositoryTest {
    @Autowired
    private VacancyRepository vacancyRepository;

    @Test
    @Sql("/scripts/init_three_vacancies.sql")
    public void findAllPageable_ok() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<Vacancy> actual = vacancyRepository.findAllPageable(pageRequest);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("slug1", actual.get(0).getSlug());
        Assertions.assertEquals("Java Developer", actual.get(0).getTitle());
        Assertions.assertEquals("Luxoft", actual.get(0).getCompanyName());
        Assertions.assertEquals("description1", actual.get(0).getDescription());
        Assertions.assertEquals("Berlin", actual.get(0).getLocation());
        Assertions.assertEquals("url1", actual.get(0).getUrl());
        Assertions.assertEquals(1, actual.get(0).getCreatedAt());
        Assertions.assertEquals(true, actual.get(0).getRemote());
    }

    @Test
    @Sql("/scripts/init_three_vacancies.sql")
    public void findTopByCreatedAtOrderByCreatedAtDesc_ok() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("createdAt"));
        List<Vacancy> actual = vacancyRepository
                .findTopByCreatedAtOrderByCreatedAtDesc(pageRequest);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("slug3", actual.get(0).getSlug());
        Assertions.assertEquals("C++ Developer", actual.get(0).getTitle());
        Assertions.assertEquals("GlobalLogic", actual.get(0).getCompanyName());
        Assertions.assertEquals("description3", actual.get(0).getDescription());
        Assertions.assertEquals("Berlin", actual.get(0).getLocation());
        Assertions.assertEquals("url3", actual.get(0).getUrl());
        Assertions.assertEquals(3, actual.get(0).getCreatedAt());
        Assertions.assertEquals(false, actual.get(0).getRemote());
    }

    @Test
    @Sql("/scripts/init_three_vacancies.sql")
    public void countAllByLocation_ok() {
        List<CountVacanciesByLocationDto> actual = vacancyRepository.countAllByLocation();
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("Berlin", actual.get(0).getLocation());
        Assertions.assertEquals(3, actual.get(0).getVacanciesPerLocation());
    }
}
