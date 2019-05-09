package ruinMapper.hexagon.infrastructure.dto.mapper;

import ruinMapper.hexagon.domain.invariant.InvariantKeeper;
import ruinMapper.hexagon.infrastructure.DtoMapper;
import ruinMapper.hexagon.infrastructure.dto.StateKeeperDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StateKeeperMapper implements
        DtoMapper<InvariantKeeper, StateKeeperDto> {
    @Override
    public InvariantKeeper toDomain(StateKeeperDto dto) {
        return null;
    }

    @Override
    public StateKeeperDto toDto(InvariantKeeper domain) {
        StateKeeperDto stateKeeperDto = new StateKeeperDto();
        stateKeeperDto.setStateKeeperId(domain.toString());
        stateKeeperDto.setAreaSet(
                convertToStringSet(domain.getAreaSet()));
        stateKeeperDto.setContextQuests(
                convertToStringSet(
                        domain.getContextQuests()));
        stateKeeperDto.setContextTags(convertToStringSet(
                domain.getContextTags()));
        stateKeeperDto.setContextQuests(convertToStringSet(
                domain.getContextQuests()));

        stateKeeperDto.setRoomToTagsMap(
                convertToStringSetMap(
                        domain.getRoomToTagsMap()));

        stateKeeperDto.setHintToRoomMap(
                convertToStringStringMap(
                        domain.getHintToRoomMap()));
        stateKeeperDto.setQuestToRoomsMap(
                convertToStringSetMap(
                        domain.getQuestToRoomsMap()));
        stateKeeperDto.setRoomToHintsMap(
                convertToStringSetMap(
                        domain.getRoomToHintsMap()));
        stateKeeperDto.setRoomToQuestsMap(
                convertToStringSetMap(
                        domain.getRoomToQuestsMap()));
        return stateKeeperDto;
    }

    private <T> Map<String, Set<String>> convertToStringSetMap(
            Map<String, Set<T>> map) {
        Map<String, Set<String>> stringMap = new HashMap<>();
        for (Map.Entry<String, Set<T>> entry : map
                .entrySet()) {

            stringMap.put(entry.getKey(),
                    convertToStringSet(entry.getValue()));
        }
        return stringMap;
    }

    private <T> Map<String, String> convertToStringStringMap(
            Map<String, T> map) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            stringMap.put(entry.getKey(),
                    entry.getValue().toString());
        }
        return stringMap;
    }

    private <T> Set<String> convertToStringSet(Set<T> set) {
        return set.stream().map(Object::toString)
                .collect(Collectors.toSet());
    }
}
