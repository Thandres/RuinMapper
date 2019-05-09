package ruinMapper.hexagon.infrastructure.dto;

import java.util.Map;
import java.util.Set;

public class StateKeeperDto {
    public Map<String, Set<String>> getRoomToQuestsMap() {
        return roomToQuestsMap;
    }

    public void setRoomToQuestsMap(
            Map<String, Set<String>> roomToQuestsMap) {
        this.roomToQuestsMap = roomToQuestsMap;
    }

    public Map<String, Set<String>> getQuestToRoomsMap() {
        return questToRoomsMap;
    }

    public void setQuestToRoomsMap(
            Map<String, Set<String>> questToRoomsMap) {
        this.questToRoomsMap = questToRoomsMap;
    }

    public Map<String, Set<String>> getRoomToHintsMap() {
        return roomToHintsMap;
    }

    public void setRoomToHintsMap(
            Map<String, Set<String>> roomToHintsMap) {
        this.roomToHintsMap = roomToHintsMap;
    }

    public Map<String, String> getHintToRoomMap() {
        return hintToRoomMap;
    }

    public void setHintToRoomMap(
            Map<String, String> hintToRoomMap) {
        this.hintToRoomMap = hintToRoomMap;
    }

    public Set<String> getContextQuests() {
        return contextQuests;
    }

    public void setContextQuests(
            Set<String> contextQuests) {
        this.contextQuests = contextQuests;
    }

    public Set<String> getAreaSet() {
        return areaSet;
    }

    public void setAreaSet(Set<String> areaSet) {
        this.areaSet = areaSet;
    }

    public Map<String, Set<String>> getRoomToTagsMap() {
        return roomToTagsMap;
    }

    public void setRoomToTagsMap(
            Map<String, Set<String>> roomToTagsMap) {
        this.roomToTagsMap = roomToTagsMap;
    }

    public Set<String> getContextTags() {
        return contextTags;
    }

    public void setContextTags(
            Set<String> contextTags) {
        this.contextTags = contextTags;
    }

    public String getStateKeeperId() {
        return stateKeeperId;
    }

    public void setStateKeeperId(String stateKeeperId) {
        this.stateKeeperId = stateKeeperId;
    }

    private Map<String, Set<String>> roomToQuestsMap;
    private Map<String, Set<String>> questToRoomsMap;
    private Map<String, Set<String>> roomToHintsMap;
    private Map<String, String> hintToRoomMap;
    private Set<String> contextQuests;
    private Set<String> areaSet;
    private Map<String, Set<String>> roomToTagsMap;
    private Set<String> contextTags;
    private String stateKeeperId;


}
