package ruinMapper.hexagon.infrastructure.dto.mapper;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.infrastructure.DtoMapper;
import ruinMapper.hexagon.infrastructure.dto.ContextDto;

public class ContextMapper implements
        DtoMapper<Context, ContextDto> {
    @Override
    public Context toDomain(ContextDto dto) {
        return null;
    }

    @Override
    public ContextDto toDto(Context domain) {
        ContextDto contextDto = new ContextDto();
        contextDto.setName(domain.accessName());

        return contextDto;
    }
}
