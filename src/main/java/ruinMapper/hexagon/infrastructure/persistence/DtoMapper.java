package ruinMapper.hexagon.infrastructure.persistence;

public interface DtoMapper<D, T> {

    public D toDomain(T dto);

    public T toDto(D domain);
}
