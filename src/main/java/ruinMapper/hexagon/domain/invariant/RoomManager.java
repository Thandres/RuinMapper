package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.model.ComponentManager;
import ruinMapper.hexagon.domain.model.HasRoom;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;


/**
 * Interface that keeps the Invariants concerning Rooms of classes tagged with HasRoom intact
 */
public interface RoomManager extends ComponentManager {


    /**
     * Invariants:
     * 1. If a Room is added to a Quest, add the Quest to the Room as well
     * 2. If a Room is added to a Hint, add the Hint to the Room as well
     * 3. Hints can only contain one room
     *
     * @param value Room to add
     * @param key   either Quest or Hint or Area
     */
    public void addRoom(RoomPort value, HasRoom key);

    /**
     * Invariants:
     * 1. If a Room is removed from a Quest, remove the Quest from the Room as well
     * 2. If a Room is Removed from a Hint, remove the Hint
     * from the Room as well and delete the Hint
     *
     * @param value Room to remove
     * @param key   either Quest or Hint
     */
    public void removeRoom(RoomPort value, HasRoom key);

    /**
     * Returns the Rooms assigned to HasRoom or Area
     *
     * @param hasRoom
     * @return
     */
    public Set<RoomPort> accessRooms(HasRoom hasRoom);

    /**
     * Tells the manager that the HasRoom Object is to be deleted and
     * does not need to be referenced anymore
     *
     * @param managedObject
     */

//    public void deleteManagedObject(HasRoom managedObject);

}
