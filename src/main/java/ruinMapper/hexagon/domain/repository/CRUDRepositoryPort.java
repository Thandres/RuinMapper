package ruinMapper.hexagon.domain.repository;

public interface CRUDRepositoryPort<T> {

    public void create(T object);

    public T read(String ID);

    public void update(T object);

    public void delete(String ID);
}
