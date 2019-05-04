package ruinMapper.hexagon.infrastructure;

public interface DtoMapper<D, T> {

    public D toDomain(T dto);

    public T toDto(D domain);
}
