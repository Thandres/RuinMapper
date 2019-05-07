package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.room.RoomPort;

/**
 * Hints are exactly that: Hints that are found in Rooms
 * The interface lets you record hints and take notes on it.
 * The status lets you quickly tag if the hint is still relevant
 * to you.
 */
public interface HintPort {
    /**
     * Changes the content of the Hint to the new content
     *
     * @param newContent the new content
     */
    public void changeContent(String newContent);

    /**
     * Returns the content of the Hint.
     *
     * @return Content of the Hint
     */
    public String accessContent();

    /**
     * Changes the notes of the Hint to the new notes
     *
     * @param newNotes The new notes
     */
    public void changeNotes(String newNotes);

    /**
     * Returns the notes of the Hint
     *
     * @return Notes of the Hint
     */
    public String accessNotes();

    /**
     * Returns the Room the Hint is assigned to.
     * A Hint always has a Room and can not exist alone.
     *
     * @return
     */
    public RoomPort accessRoom();

    /**
     * Deletes the Hint from the Context.
     * The Hint is also removed from its assigned Room.
     */
    public void deleteHint();

    /**
     * Returns the current Status of the Hint for easier categorization of Hints
     * @return Current HintStatus
     */
    public HintStatus getHintStatus();

    /**
     * Changes the HintStatus to the new HintStatus
     *
     * @param newStatus
     */
    public void setHintStatus(HintStatus newStatus);

}
