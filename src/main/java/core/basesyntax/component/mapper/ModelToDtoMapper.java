package core.basesyntax.component.mapper;

public interface ModelToDtoMapper<D, M> {
    D toDto(M model);
}
