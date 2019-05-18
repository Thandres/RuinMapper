package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.UUID;

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
                dto.getRoomID(),
                dto.getContextID(),
                repository,
                contextRepository, roomRepository,
                tagRepository,
                UUID.fromString(dto.getHintID()));
        domain.setStatus(dto.getStatus());
        domain.setNotes(dto.getNotes());

        domain.setKeywords(dto.getKeywords());
        return domain;
    }

    @Override
    public HintDto toDto(Hint domain) {
        HintDto hintDto = new HintDto();
        hintDto.setContent(domain.getContent());
        hintDto.setNotes(domain.getNotes());
        hintDto.setRoomID(domain.getRoomID());
        hintDto.setStatus(domain.getHintStatus());
        hintDto.setKeywords(
                domain.getKeywords());
        hintDto.setContextID(
                domain.getContextID());
        hintDto.setHintID(domain.toString());
        return hintDto;
    }
}
