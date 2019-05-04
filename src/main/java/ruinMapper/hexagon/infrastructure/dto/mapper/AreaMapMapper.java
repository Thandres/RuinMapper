package ruinMapper.hexagon.infrastructure.dto.mapper;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.infrastructure.DtoMapper;
import ruinMapper.hexagon.infrastructure.dto.AreaMapDto;

public class AreaMapMapper implements
        DtoMapper<Area, AreaMapDto> {
    private RoomMapper roomMapper;

    public AreaMapMapper() {
        roomMapper = new RoomMapper();
    }

    @Override
    public Area toDomain(AreaMapDto dto) {

        return null;
    }

    @Override
    public AreaMapDto toDto(Area domain) {
        return null;
    }


}
