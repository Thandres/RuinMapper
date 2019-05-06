package ruinMapper.hexagon.domain.area;

import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.Set;

/**
 * Keeps track of rooms and all hints on the area
 */
public interface AreaPort {
    /**
     * Creates a room with the coordinates in the area and returns it for ease of access
     *
     * @param x X-Coordinate in a 2 dimensional grid
     * @param y Y-Coordinate in a 2 dimensional grid
     * @return
     */
    public RoomPort createRoom(int x, int y);

    /**
     * Returns the room saved at the specified coordinates
     *
     * @param x X-Coordinate of the room to access
     * @param y Y-Coordinate of the room to access
     * @return
     */
    public RoomPort accessRoom(int x, int y);

    /**
     * Returns all Rooms assigned to the Area
     *
     * @return Set of rooms assigned to the Area
     */
    public Set<RoomPort> accessRooms();

    /**
     * Deletes the Room from the Area.
     * A deleted Room is also removed from any Quest and Hints
     * are deleted as well.
     *
     * @param x
     * @param y
     */
    public void deleteRoom(int x, int y);

    /**
     * Changes the title of the Area to the new title.
     *
     * @param newTitle Title that will be returned by accessTitle()
     */
    public void changeTitle(String newTitle);

    /**
     * Returns the current title of the Area.
     *
     * @return Title of the area
     */
    public String accessTitle();

    /**
     * Changes the notes of the Area to the new notes.
     *
     * @param newNotes Notes for the Area as a whole
     */
    public void changeNotes(String newNotes);

    /**
     * Returns the current notes taken for the Area
     *
     * @return Current notes
     */
    public String accessNotes();

    /**
     * Returns a Set of Hints that are in the Rooms assigned to this Area
     *
     * @return All Hints in the Area by transitive property
     */
    public Set<HintPort> accessHintsOnArea();

    /**
     * Deletes the Area.
     * Any Room assigned to the deleted Area is deleted as well.
     */
    public void deleteArea();
}
