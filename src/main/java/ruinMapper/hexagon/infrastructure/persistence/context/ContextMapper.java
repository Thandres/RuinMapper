package ruinMapper.hexagon.infrastructure.persistence.context;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

public class ContextMapper implements
        DtoMapper<Context, ContextDto> {
    private CRUDRepositoryPort<Area> areaRepository;
    private CRUDRepositoryPort<Tag> tagRepository;
    private CRUDRepositoryPort<Quest> questRepository;


    public ContextMapper(
            CRUDRepositoryPort<Area> areaRepository,
            CRUDRepositoryPort<Tag> tagRepository,
            CRUDRepositoryPort<Quest> questRepository) {
        this.areaRepository = areaRepository;
        this.tagRepository = tagRepository;
        this.questRepository = questRepository;
    }

    @Override
    public Context toDomain(ContextDto dto,
                            CRUDRepositoryPort<Context> repository) {
        Context domain = new Context(dto.getName(),
                repository, tagRepository, questRepository,
                areaRepository);
        domain.setAreas(dto.getAreas());
        domain.setQuests(dto.getQuests());
        domain.setValidTags(dto.getValidTags());
        domain.setKeywords(dto.getKeywords());
        return domain;
    }

    @Override
    public ContextDto toDto(Context domain) {
        ContextDto contextDto = new ContextDto();
        contextDto.setName(domain.accessName());
        contextDto.setAreas(domain.getAreas());
        contextDto
                .setQuests(domain.getQuests());
        contextDto.setValidTags(
                domain.getValidTags());
        contextDto.setKeywords(
                domain.getKeywords());
        return contextDto;
    }
}
