package ruinMapper.hexagon.domain.tag;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

import java.util.UUID;

//TODO Validation
public class Tag implements TagPort {
    private String tagType;
    private CRUDRepositoryPort<Tag> tagRepository;
    private TaggableManager taggableManager;

    private UUID tagID;

    public Tag(String tagType,
               CRUDRepositoryPort<Tag> tagRepository,
               TaggableManager taggableManager,
               UUID tagID) {
        this.tagType = tagType;
        this.tagRepository = tagRepository;
        this.taggableManager = taggableManager;

        this.tagID = tagID;
    }

    @Override
    public String accessTag() {
        return tagType;
    }

    @Override
    public void changeTag(String newText) {
        if (!tagType.equals(newText)) {
            tagType = newText;
            saveState();
        }
    }

    @Override
    public void deleteTag() {
        taggableManager.deleteTag(this);
        tagRepository.delete(tagID.toString());
    }

    private void saveState() {
        tagRepository.update(this);
    }
}
