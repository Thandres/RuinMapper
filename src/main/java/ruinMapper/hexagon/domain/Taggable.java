package ruinMapper.hexagon.domain;

/**
 * Marker interface used to lessen invariant complexity.
 * isContext() is useful for implementing invariants
 */
public interface Taggable {

    public boolean isContext();
}
