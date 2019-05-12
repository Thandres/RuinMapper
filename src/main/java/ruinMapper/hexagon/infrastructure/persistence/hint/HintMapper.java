package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.Set;
import java.util.UUID;

import static ruinMapper.hexagon.infrastructure.persistence.MappingHelper.toDomainSet;
import static ruinMapper.hexagon.infrastructure.persistence.MappingHelper.toStringSet;

public class HintMapper implements
        DtoMapper<Hint, HintDto> {

    private CRUDRepositoryPort<Room> roomRepository;
    private CRUDRepositoryPort<Context> contextRepository;
    private CRUDRepositoryPort<Tag> tagRepository;


    public HintMapper(
            CRUDRepositoryPort<Room> roomRepository,
            CRUDRepositoryPort<Context> contextRepository,
            CRUDRepositoryPort<Tag> tagRepository) {
        this.roomRepository = roomRepository;
        this.contextRepository = contextRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Hint toDomain(HintDto dto,
                         CRUDRepositoryPort<Hint> repository) {
        Hint domain = new Hint(dto.getContent(),
                roomRepository.read(dto.getRoomID()),
                contextRepository.read(dto.getContextID()),
                repository,
                UUID.fromString(dto.getHintID()));
        domain.setStatus(dto.getStatus());
        domain.setNotes(dto.getNotes());
        Set<TagPort> keywords = toDomainSet(
                dto.getKeywords(), tagRepository);
        domain.setKeywords(keywords);
        return domain;
    }

    @Override
    public HintDto toDto(Hint domain) {
        HintDto hintDto = new HintDto();
        hintDto.setContent(domain.accessContent());
        hintDto.setNotes(domain.accessNotes());
        hintDto.setRoomID(domain.accessRoom().toString());
        hintDto.setStatus(domain.getHintStatus());
        hintDto.setKeywords(
                toStringSet(domain.getKeywords()));
        hintDto.setContextID(
                domain.getContext().toString());
        hintDto.setHintID(domain.toString());
        return hintDto;
    }
}
