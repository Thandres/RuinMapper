package ruinMapper.hexagon.domain.model;

/**
 * Marker interface used to lessen invariant complexity.
 * Indicates that the implementing class uses a Manager to access
 * the indicated component
 */
public interface ComponentTag {
    public ComponentType getType();
}
