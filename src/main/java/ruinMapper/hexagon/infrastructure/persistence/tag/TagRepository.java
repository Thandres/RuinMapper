package ruinMapper.hexagon.infrastructure.persistence.tag;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

import java.util.HashMap;
import java.util.Map;

public class TagRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Tag> {

    private Map<String, Tag> loadedTags;
    private DtoMapper<Tag, TagDto> tagMapper;

    public TagRepository(String directoryPath,
                         DtoMapper<Tag, TagDto> tagMapper) {
        super(directoryPath);
        this.tagMapper = tagMapper;
        loadedTags = new HashMap<>();
    }

    @Override
    public void create(Tag object) {
        loadedTags.put(object.toString(), object);
        TagDto tagDto = tagMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                tagDto);
    }

    @Override
    public Tag read(String ID) {
        if (loadedTags.containsKey(ID)) {
            return loadedTags.get(ID);
        } else {
            TagDto tagDto = FileHelper
                    .readFromFile(createFilelocation(ID),
                            TagDto.class);
            Tag loadedTag = tagMapper
                    .toDomain(tagDto, this);
            loadedTags.put(loadedTag.toString(), loadedTag);
            return loadedTag;
        }
    }

    @Override
    public void update(Tag object) {
        TagDto tagDto = tagMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                tagDto);
    }

    @Override
    public void delete(String ID) {
        if (loadedTags.containsKey(ID)) {
            loadedTags.remove(ID);
        }
        FileHelper.deleteFile(createFilelocation(ID));
    }
}
