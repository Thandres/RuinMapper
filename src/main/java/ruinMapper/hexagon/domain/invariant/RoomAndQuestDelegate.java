package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.ComponentType;
import ruinMapper.hexagon.domain.HasQuest;
import ruinMapper.hexagon.domain.HasRoom;
import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ruinMapper.hexagon.domain.invariant.CircularManagmentHelper.*;

// Handles the HasQuest and HasRoom Invariants
public class RoomAndQuestDelegate implements
        QuestManager,
        RoomManager {
    private Map<String, Set<QuestPort>> roomToQuestsMap;
    private Map<String, Set<RoomPort>> questToRoomsMap;
    private Map<String, Set<HintPort>> roomToHintsMap;
    private Map<String, RoomPort> hintToRoomMap;
    private Set<QuestPort> contextQuests;

    public RoomAndQuestDelegate(
            Map<String, Set<QuestPort>> roomToQuestsMap,
            Map<String, Set<RoomPort>> questToRoomsMap,
            Set<QuestPort> contextQuests) {
        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.contextQuests = contextQuests;
    }

    private void deleteQuestImpl(QuestPort questToDelete) {
        contextQuests.remove(questToDelete);
        deleteLinkedRecord(questToRoomsMap,
                roomToQuestsMap, questToDelete);
    }


    @Override
    public void addQuest(QuestPort value, HasQuest key) {
        // Invariant 1
        if (ComponentType.ROOM.equals(key.getType())) {
            linkedAdd(roomToQuestsMap, questToRoomsMap,
                    (RoomPort) key, value);
        }
        // cant add duplicate objects to a set, so adding
        // is either successful or the object is already there
        contextQuests.add(value);
    }

    @Override
    public void removeQuest(QuestPort value,
                            HasQuest key) {
        if (ComponentType.ROOM.equals(key.getType())) {
            // Invariant 1
            linkedRemove(roomToQuestsMap, questToRoomsMap,
                    (RoomPort) key, value);
        } else {
            // Invariant 2
            deleteQuestImpl(value);
        }
    }

    @Override
    public Set<QuestPort> accessQuests(
            HasQuest hasQuest) {
        if (ComponentType.ROOM.equals(hasQuest.getType())) {
            return new HashSet<>(roomToQuestsMap
                    .get(hasQuest.toString()));
        } else {
            return new HashSet<>(contextQuests);
        }
    }

    //#############ROOMMANAGER####################
    @Override
    public void addRoom(RoomPort value, HasRoom key) {
        if (ComponentType.QUEST.equals(key.getType())) {
            // Invariant 1
            linkedAdd(questToRoomsMap, roomToQuestsMap,
                    (QuestPort) key,
                    value);
        } else {
            // Invariant 2 and 3
            hintToRoomMap.put(key.toString(), value);
            addToSetMap(roomToHintsMap, value.toString(),
                    (HintPort) key);
        }
    }

    @Override
    public void removeRoom(RoomPort value, HasRoom key) {
        if (ComponentType.QUEST.equals(key.getType())) {
            // Invariant 1
            linkedRemove(questToRoomsMap, roomToQuestsMap,
                    (QuestPort) key,
                    value);
        } else {
            // Invariant 2
            hintToRoomMap.remove(key.toString());
        }
    }

    @Override
    public Set<RoomPort> accessRooms(HasRoom hasRoom) {
        return new HashSet<>(
                questToRoomsMap.get(hasRoom.toString()));
    }

    @Override
    public void deleteQuest(QuestPort questPort) {
        // Invariant 1
        deleteQuestImpl(questPort);
    }
}
