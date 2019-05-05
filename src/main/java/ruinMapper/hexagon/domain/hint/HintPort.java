package ruinMapper.hexagon.domain.hint;

import ruinMapper.hexagon.domain.room.RoomPort;

/**
 * Hints are exactly that: Hints that are found in Rooms
 * The interface lets you record hints and take notes on it.
 * The status lets you quickly tag if the hint is still relevant
 * to you.
 */
public interface HintPort {
    public void changeContent(String newContent);

    public String accessContent();

    public void changeNotes(String newNotes);

    public String accessNotes();

    public RoomPort accessRoom();

    public void deleteHint();

    public HintStatus getHintStatus();

    public void setHintStatus(HintStatus status);

}
