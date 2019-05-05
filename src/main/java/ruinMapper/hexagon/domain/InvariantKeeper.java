package ruinMapper.hexagon.domain;

import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.quest.RoomManager;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.RoomPort;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.domain.tag.TaggableManager;

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
    private TagAndTaggableDelegate tagAndTaggableDelegate;

    private CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository;
    private UUID stateKeeperID;

    public InvariantKeeper(
            RoomAndQuestableDelegate roomAndQuestableDelegate,
            TagAndTaggableDelegate tagAndTaggableDelegate,
            CRUDRepositoryPort<InvariantKeeper> invariantKeeperRepository,
            UUID stateKeeperID) {
        this.roomAndQuestableDelegate = roomAndQuestableDelegate;
        this.tagAndTaggableDelegate = tagAndTaggableDelegate;
        this.invariantKeeperRepository = invariantKeeperRepository;
        this.stateKeeperID = stateKeeperID;
    }

    private void saveState() {
        invariantKeeperRepository.update(this);
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
        tagAndTaggableDelegate.addTag(value, key);
        saveState();
    }

    @Override
    public void removeTag(TagPort value, Taggable key) {
        tagAndTaggableDelegate.removeTag(value, key);
        saveState();
    }

    @Override
    public Set<TagPort> accessTags(Taggable taggable) {
        return tagAndTaggableDelegate.accessTags(taggable);
    }

    /**********************************************************/
    //TaggableManager
    @Override
    public void deleteTag(TagPort tagPort) {
        tagAndTaggableDelegate.deleteTag(tagPort);
        saveState();
    }
}
