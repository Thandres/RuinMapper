package ruinMapper.hexagon.infrastructure.persistence.mapper;

import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.HintDto;

public class HintMapper implements
        DtoMapper<Hint, HintDto> {
    @Override
    public Hint toDomain(HintDto dto) {
        return null;
    }

    @Override
    public HintDto toDto(Hint domain) {
        HintDto hintDto = new HintDto();
        hintDto.setContent(domain.accessContent());
        hintDto.setNotes(domain.accessNotes());
        hintDto.setStatus(domain.getHintStatus());
        hintDto.setHintId(domain.toString());
        return hintDto;
    }
}
