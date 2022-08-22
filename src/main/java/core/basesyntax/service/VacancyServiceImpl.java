package core.basesyntax.service;

import core.basesyntax.component.client.HttpClient;
import core.basesyntax.component.mapper.impl.ApiVacancyMapper;
import core.basesyntax.dto.ApiResponseDto;
import core.basesyntax.dto.CountVacanciesByLocationDto;
import core.basesyntax.model.Vacancy;
import core.basesyntax.repository.VacancyRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class VacancyServiceImpl implements VacancyService {
    private static final String API_URL = "https://www.arbeitnow.com/api/job-board-api";

    private final HttpClient httpClient;
    private final VacancyRepository vacancyRepository;
    private final ApiVacancyMapper apiVacancyMapper;

    public VacancyServiceImpl(HttpClient httpClient, VacancyRepository vacancyRepository,
                              ApiVacancyMapper apiVacancyMapper) {
        this.httpClient = httpClient;
        this.vacancyRepository = vacancyRepository;
        this.apiVacancyMapper = apiVacancyMapper;
    }

    @Override
    public List<Vacancy> findAll(PageRequest pageRequest) {
        return vacancyRepository.findAllPageable(pageRequest);
    }

    @Override
    public List<Vacancy> findTopByCreatedAt(PageRequest pageRequest) {
        return vacancyRepository.findTopByCreatedAtOrderByCreatedAtDesc(pageRequest);
    }

    @Override
    public List<CountVacanciesByLocationDto> countVacanciesByLocation() {
        return vacancyRepository.countAllByLocation();
    }

    @Override
    @Scheduled(cron = "0 0 */1 * * ?")
    @EventListener(ApplicationReadyEvent.class)
    public void syncExternalVacancies() {
        log.info("syncExternalVacancies() invoked at " + LocalDateTime.now());
        vacancyRepository.deleteAll();
        ApiResponseDto apiResponseDto = httpClient.get(API_URL, ApiResponseDto.class);
        for (int i = 0; i < 5; i++) {
            List<Vacancy> data = apiResponseDto.getData().stream()
                    .map(apiVacancyMapper::toModel)
                    .collect(Collectors.toList());
            vacancyRepository.saveAll(data);
            if (i >= 4) {
                break;
            }
            apiResponseDto = httpClient.get(apiResponseDto.getLinks().getNext(),
                    ApiResponseDto.class);
        }
        log.info("syncExternalVacancies() completed at " + LocalDateTime.now());
    }
}
