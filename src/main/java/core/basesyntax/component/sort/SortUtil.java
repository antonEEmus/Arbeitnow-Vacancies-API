package core.basesyntax.component.sort;

import org.springframework.data.domain.Sort;

public interface SortUtil {
    Sort getSort(String sortBy);
}
