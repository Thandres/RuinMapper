package ruinMapper.hexagon.infrastructure.persistence.tag;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;

import java.util.UUID;

public class TagMapper implements DtoMapper<Tag, TagDto> {

    private CRUDRepositoryPort<Context> contextRepository;

    @Override
    public Tag toDomain(TagDto dto,
                        CRUDRepositoryPort<Tag> repository) {
        Tag domain = new Tag(dto.getTagType(),
                contextRepository.read(dto.getContextID()),
                repository,
                UUID.fromString(dto.getTagID()));
        return domain;
    }

    @Override
    public TagDto toDto(Tag domain) {
        TagDto tagDto = new TagDto();
        tagDto.setContextID(domain.getContext().toString());
        tagDto.setTagType(domain.accessTag());
        tagDto.setTagID(domain.toString());
        return tagDto;
    }
}
