package ruinMapper.hexagon.domain.model;

public abstract class ComponentSuper implements
        HasRepository {

    // Overrides toString to return the Objects ID, so the repository has an easier time
    @Override
    public abstract String toString();
}
