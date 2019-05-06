package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.hint.HintPort;
import ruinMapper.hexagon.domain.model.*;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.room.RoomPort;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ruinMapper.hexagon.domain.invariant.CircularManagmentHelper.*;

// Handles the HasQuest and HasRoom Invariants
public class RoomAndQuestAndHintDelegate implements
        QuestManager,
        RoomManager, HintManager {
    private Map<String, Set<QuestPort>> roomToQuestsMap;
    private Map<String, Set<RoomPort>> questToRoomsMap;
    private Map<String, Set<HintPort>> roomToHintsMap;
    private Map<String, RoomPort> hintToRoomMap;
    private Set<QuestPort> contextQuests;

    public RoomAndQuestAndHintDelegate(
            Map<String, Set<QuestPort>> roomToQuestsMap,
            Map<String, Set<RoomPort>> questToRoomsMap,
            Map<String, Set<HintPort>> roomToHintsMap,
            Map<String, RoomPort> hintToRoomMap,
            Set<QuestPort> contextQuests) {
        this.roomToQuestsMap = roomToQuestsMap;
        this.questToRoomsMap = questToRoomsMap;
        this.roomToHintsMap = roomToHintsMap;
        this.hintToRoomMap = hintToRoomMap;
        this.contextQuests = contextQuests;
    }

    private void deleteQuestImpl(QuestPort questToDelete) {
        contextQuests.remove(questToDelete);
        deleteLinkedRecord(questToRoomsMap,
                roomToQuestsMap, questToDelete);
    }

    private void deleteHintImpl(HintPort hintPort) {
        hintToRoomMap.remove(hintPort.toString());
        deleteRecord(roomToHintsMap, hintPort);
    }

    //#############QUESTMANAGER####################

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
        if (ComponentType.QUEST.equals(hasRoom.getType())) {
            return new HashSet<>(
                    questToRoomsMap
                            .get(hasRoom.toString()));
        } else {//TODO maybe explicit type check?
            RoomPort room = hintToRoomMap
                    .get(hasRoom.toString());
            Set<RoomPort> temp = new HashSet<>();
            temp.add(room);
            return temp;
        }
    }

    //#############HINTMANAGER####################

    @Override
    public void addHint(HintPort value, HasHint key) {
        addToSetMap(roomToHintsMap, key.toString(), value);
        if (ComponentType.ROOM.equals(key.getType())) {
            hintToRoomMap
                    .put(value.toString(), (RoomPort) key);
        }
    }

    @Override
    public void removeHint(HintPort value, HasHint key) {

        if (ComponentType.ROOM.equals(key.getType())) {
            deleteHintImpl(value);
        } else {
            removeFromMap(roomToHintsMap, key.toString(),
                    value);
        }
    }

    @Override
    public Set<HintPort> accessHints(HasHint hasHint) {
        return new HashSet<>(
                roomToHintsMap.get(hasHint.toString()));
    }


    @Override
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject) {
        switch (managedObject.getType()) {
            case QUEST:
                deleteQuestImpl((QuestPort) managedObject);
                break;
            case HINT:
                deleteHintImpl((HintPort) managedObject);
                break;
        }
    }
}
