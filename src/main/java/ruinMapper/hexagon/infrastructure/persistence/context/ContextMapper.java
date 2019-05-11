package ruinMapper.hexagon.infrastructure.persistence.context;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.quest.Quest;
import ruinMapper.hexagon.domain.quest.QuestPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.domain.tag.TagPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class ContextMapper implements
        DtoMapper<Context, ContextDto> {
    private CRUDRepositoryPort<Area> areaRepository;
    private CRUDRepositoryPort<Tag> tagRepository;
    private CRUDRepositoryPort<Quest> questRepository;


    public ContextMapper(
            CRUDRepositoryPort<Area> areaRepository,
            CRUDRepositoryPort<Tag> tagRepository) {
        this.areaRepository = areaRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Context toDomain(ContextDto dto,
                            CRUDRepositoryPort<Context> repository) {
        Context domain = new Context(dto.getName(),
                repository);
        Set<AreaPort> areas = toDomainSet(dto.getAreas(),
                areaRepository);
        domain.setAreas(areas);

        Set<QuestPort> quests = toDomainSet(dto.getQuests(),
                questRepository);
        domain.setQuests(quests);

        Set<TagPort> tags = toDomainSet(dto.getValidTags(),
                tagRepository);
        domain.setValidTags(tags);

        return domain;
    }

    @Override
    public ContextDto toDto(Context domain) {
        ContextDto contextDto = new ContextDto();
        contextDto.setName(domain.accessName());
        contextDto.setAreas(toStringSet(domain.getAreas()));
        contextDto
                .setQuests(toStringSet(domain.getQuests()));
        contextDto.setValidTags(
                toStringSet(domain.getValidTags()));
        return contextDto;
    }

    private <T> Set<String> toStringSet(Set<T> tSet) {
        return tSet.stream().map(Object::toString)
                .collect(Collectors.toSet());
    }

    private <T extends D, D> Set<D> toDomainSet(
            Set<String> stringSet,
            CRUDRepositoryPort<T> repository) {
        return stringSet.stream()
                .map(repository::read)
                .collect(Collectors.toSet());

    }
}
