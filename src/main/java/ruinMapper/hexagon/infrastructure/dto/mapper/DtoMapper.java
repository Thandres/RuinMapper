package ruinMapper.hexagon.infrastructure.dto.mapper;

public interface DtoMapper<D, T> {

    public D toDomain(T dto);

    public T toDto(D domain);
}
