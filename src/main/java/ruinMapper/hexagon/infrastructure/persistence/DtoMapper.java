package ruinMapper.hexagon.infrastructure.persistence;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public interface DtoMapper<D, T> {

    public D toDomain(T dto,
                      CRUDRepositoryPort<D> repository);

    public T toDto(D domain);
}
