package core.basesyntax.controller;

import core.basesyntax.dto.CountVacanciesByLocationDto;
import core.basesyntax.model.Vacancy;
import core.basesyntax.service.VacancyService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class VacancyControllerTest {
    @MockBean
    private VacancyService vacancyService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        List<Vacancy> vacancies = List.of(new Vacancy(1L, "slug1", "Luxoft", "Java Developer",
                "description1", true, "url1", "Berlin", 1L));

        List<Vacancy> topVacancy = List.of(new Vacancy(3L, "slug3", "GlobalLogic", "C++ Developer",
                "description3", false, "url3", "Berlin", 3L));

        List<CountVacanciesByLocationDto> statisticDtos = List.of(
                new CountVacanciesByLocationDto("Berlin", 3L));

        Mockito.when(vacancyService.findAll(ArgumentMatchers.any())).thenReturn(vacancies);
        Mockito.when(vacancyService.findTopByCreatedAt(ArgumentMatchers.any()))
                .thenReturn(topVacancy);
        Mockito.when(vacancyService.countVacanciesByLocation()).thenReturn(statisticDtos);
    }

    @Test
    public void findAll_ok() {
        RestAssuredMockMvc.when()
                .get("/vacancies?sortBy=id:ASC&count=1")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].title", Matchers.equalTo("Java Developer"))
                .body("[0].companyName", Matchers.equalTo("Luxoft"))
                .body("[0].description", Matchers.equalTo("description1"))
                .body("[0].remote", Matchers.equalTo(true))
                .body("[0].url", Matchers.equalTo("url1"))
                .body("[0].location", Matchers.equalTo("Berlin"))
                .body("[0].createdAt", Matchers.equalTo(1));
    }

    @Test
    public void findTopByCreationDate_ok() {
        RestAssuredMockMvc.when()
                .get("/vacancies/top-recent?count=1")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(3))
                .body("[0].title", Matchers.equalTo("C++ Developer"))
                .body("[0].companyName", Matchers.equalTo("GlobalLogic"))
                .body("[0].description", Matchers.equalTo("description3"))
                .body("[0].remote", Matchers.equalTo(false))
                .body("[0].url", Matchers.equalTo("url3"))
                .body("[0].location", Matchers.equalTo("Berlin"))
                .body("[0].createdAt", Matchers.equalTo(3));
    }

    @Test
    public void countVacanciesByLocation_ok() {
        RestAssuredMockMvc.when()
                .get("/vacancies/statistics/vacancies-per-location")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].location", Matchers.equalTo("Berlin"))
                .body("[0].vacanciesPerLocation", Matchers.equalTo(3));
    }
}
