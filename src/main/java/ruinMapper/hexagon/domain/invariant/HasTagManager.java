package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.model.ComponentManager;
import ruinMapper.hexagon.domain.model.ComponentTag;

/**
 * Interface that keeps the invariants of Tags intact
 */
public interface HasTagManager extends ComponentManager {

    /**
     * Invariant:
     * When a Tag is deleted make sure every HasTag removed
     * the reference to the removed Tag
     * <p>
     * Overrriden for visibility of interface Functionality
     *
     * @param managedObject
     * @param <T>
     */
    @Override
    <T extends ComponentTag> void deleteManagedObject(
            T managedObject);
}
