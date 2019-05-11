package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.room.Room;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.UUID;

public class HintMapper implements
        DtoMapper<Hint, HintDto> {

    private CRUDRepositoryPort<Room> roomRepository;

    public HintMapper(
            CRUDRepositoryPort<Room> roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Hint toDomain(HintDto dto,
                         CRUDRepositoryPort<Hint> repository) {
        Hint domain = new Hint(dto.getContent(),
                roomRepository.read(dto.getRoomID()),
                repository);
        domain.setStatus(dto.getStatus());
        domain.setHintID(UUID.fromString(dto.getHintID()));
        domain.setNotes(dto.getNotes());
        return domain;
    }

    @Override
    public HintDto toDto(Hint domain) {
        HintDto hintDto = new HintDto();
        hintDto.setContent(domain.accessContent());
        hintDto.setNotes(domain.accessNotes());
        hintDto.setRoomID(domain.accessRoom().toString());
        hintDto.setStatus(domain.getHintStatus());
        hintDto.setHintID(domain.toString());
        return hintDto;
    }
}
