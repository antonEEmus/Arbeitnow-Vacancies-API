package core.basesyntax.controller;

import core.basesyntax.component.mapper.impl.VacancyMapper;
import core.basesyntax.component.sort.SortUtil;
import core.basesyntax.dto.CountVacanciesByLocationDto;
import core.basesyntax.dto.VacancyResponseDto;
import core.basesyntax.service.VacancyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    private final VacancyMapper vacancyMapper;
    private final SortUtil sortUtil;

    public VacancyController(VacancyService vacancyService, VacancyMapper vacancyMapper,
                             SortUtil sortUtil) {
        this.vacancyService = vacancyService;
        this.vacancyMapper = vacancyMapper;
        this.sortUtil = sortUtil;
    }

    @GetMapping
    public List<VacancyResponseDto> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer count,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Sort sort = sortUtil.getSort(sortBy);
        PageRequest pageRequest = PageRequest.of(page, count, sort);
        return vacancyService.findAll(pageRequest).stream()
                .map(vacancyMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/top-recent")
    public List<VacancyResponseDto> findTopByCreationDate(
            @RequestParam(defaultValue = "10") Integer limit
    ) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(order));
        return vacancyService.findTopByCreatedAt(pageRequest).stream()
                .map(vacancyMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/statistics/vacancies-per-location")
    public List<CountVacanciesByLocationDto> countVacanciesByLocation() {
        return vacancyService.countVacanciesByLocation();
    }
}
