package ruinMapper.hexagon.domain.model;

public interface ComponentManager {
    /**
     * Tells the ComponentManager that the managedObjects no longer needs to be managed.
     * Implementing class needs to make sure that every reference is removed, the managedObject handles its won deletion
     *
     * @param managedObject
     * @param <T>
     */
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject);
}
