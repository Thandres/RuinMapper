package ruinMapper.hexagon.domain.model;

/**
 * Marker interface used to lessen invariant complexity.
 * Indicates the type of Manager the implementing class uses to access
 * the components
 */
public interface HasComponent {
    public ComponentType getType();
}
