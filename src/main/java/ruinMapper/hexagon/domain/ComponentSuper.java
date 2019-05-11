package ruinMapper.hexagon.domain;

public abstract class ComponentSuper {

    // Overrides toString to return the Objects ID, so the repository has an easier time
    @Override
    public abstract String toString();

    public abstract void saveState();
}
