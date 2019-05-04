package ruinMapper.hexagon.domain.room;

import ruinMapper.hexagon.domain.Taggable;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;

import java.util.Set;

/**
 * Manages all aspects about a room. Allows for takning notes,
 * creating and adding quests to rooms, adding hints and marking
 * the room with custom marks.
 */
public interface RoomPort extends Taggable {
    public void changeTitle(String newTitle);

    public String accessTitle();

    public void changeNotes(String newNotes);

    public String accessNotes();

    public HintPort createHint(String content);

    public void addHint(HintPort hint);

    public void removeHint(HintPort hint);

    public Set<HintPort> accessHints();

    public QuestPort createQuest(String title);

    public void addQuest(QuestPort quest);

    public void removeQuest(QuestPort quest);

    public Set<QuestPort> accessQuests();

}