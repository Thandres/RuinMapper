package ruinMapper.hexagon.domain.model;

/**
 * Marker interface used to lessen invariant complexity.
 */
public interface HasComponent {
    public ComponentType getType();
}
