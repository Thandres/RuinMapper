package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.quest.RoomManager;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.domain.tag.TaggableManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * Class that implements all the Manager interfaces to keep the
 * invariants of the domain intact
 */
public class InvariantKeeper implements
        QuestManager, RoomManager, TagManager,
        TaggableManager {
    //TODO implement the IDs of component in a pre/postfix way so the maps can save strings instead of whole objects

    private RoomAndQuestableDelegate roomAndQuestableDelegate;


    //TODO implement the TaggableManager and TagManager interface
    private Map<RoomPort, Set<TagPort>> roomToTagsMap;
    private Map<TagPort, Set<RoomPort>> tagToRoomsMap;
    private Set<TagPort> contextTags;

    private CRUDRepositoryPort<InvariantKeeper> stateRepository;
    private UUID stateKeeperID;

    public InvariantKeeper(
            RoomAndQuestableDelegate roomAndQuestableDelegate,
            CRUDRepositoryPort<InvariantKeeper> stateRepository,
            UUID stateKeeperID) {
        this.roomAndQuestableDelegate = roomAndQuestableDelegate;
        this.stateRepository = stateRepository;
        this.stateKeeperID = stateKeeperID;
    }

    private void saveState() {
        stateRepository.update(this);
    }


    private void deleteTagImpl(TagPort tagPort) {
        contextTags.remove(tagPort);
        deleteRecord(roomToTagsMap, tagPort);
    }
    //Interface implementations

    /*******************************************************/

    // QuestManager
    @Override
    public void addQuest(QuestPort value,
                         Questable key) {
        roomAndQuestableDelegate.addQuest(value, key);
        saveState();
    }

    @Override
    public void removeQuest(QuestPort value,
                            Questable key) {
        roomAndQuestableDelegate.removeQuest(value, key);
        saveState();

    }

    @Override
    public Set<QuestPort> accessQuests(
            Questable questable) {
        return roomAndQuestableDelegate
                .accessQuests(questable);
    }

    /**********************************************************/
// RoomManager
    @Override
    public void addRoom(RoomPort value,
                        QuestPort key) {
        roomAndQuestableDelegate.addRoom(value, key);
        saveState();
    }

    @Override
    public void removeRoom(RoomPort value,
                           QuestPort key) {
        roomAndQuestableDelegate.removeRoom(value, key);
        saveState();
    }

    @Override
    public Set<RoomPort> accessRooms(QuestPort questPort) {
        return roomAndQuestableDelegate
                .accessRooms(questPort);
    }

    @Override
    public void deleteQuest(QuestPort questPort) {
        roomAndQuestableDelegate.deleteQuest(questPort);
        saveState();
    }

    /**********************************************************/
// TagManager
    @Override
    public void addTag(TagPort value, Taggable key) {
        if (key.isContext()) {
            // Invariant 1
            contextTags.add(value);
        } else {
            // Invariant 2
            if (contextTags.contains(value)) {
                addToMap(roomToTagsMap, (RoomPort) key,
                        value);
            } else {
                //TODO invalid tag handling
            }
        }
    }

    @Override
    public void removeTag(TagPort value, Taggable key) {
        if (key.isContext()) {
            // Invariant 1
            deleteTagImpl(value);
        } else {
            // Invariant 2
            removeFromMap(roomToTagsMap, (RoomPort) key,
                    value);
        }
    }

    @Override
    public Set<TagPort> accessTags(Taggable taggable) {
        if (taggable.isContext()) {
            return new HashSet<>(contextTags);
        } else {
            return new HashSet<>(
                    roomToTagsMap.get((RoomPort) taggable));
        }
    }

    /**********************************************************/
    //TaggableManager
    @Override
    public void deleteTag(TagPort tagPort) {
        // Invariant 1
        deleteTagImpl(tagPort);
        saveState();
    }
}
