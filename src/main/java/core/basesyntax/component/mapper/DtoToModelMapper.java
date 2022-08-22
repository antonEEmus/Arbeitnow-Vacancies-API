package core.basesyntax.component.mapper;

public interface DtoToModelMapper<D, M> {
    M toModel(D dto);
}
