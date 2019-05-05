package ruinMapper.hexagon.domain.tag;

import ruinMapper.hexagon.domain.marker.ComponentSuper;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

import java.util.UUID;

//TODO Validation
public class Tag extends ComponentSuper implements TagPort {
    private String tagType;
    private CRUDRepositoryPort<Tag> tagRepository;
    private HasTagManager hasTagManager;

    private UUID tagID;

    public Tag(String tagType,
               CRUDRepositoryPort<Tag> tagRepository,
               HasTagManager hasTagManager,
               UUID tagID) {
        this.tagType = tagType;
        this.tagRepository = tagRepository;
        this.hasTagManager = hasTagManager;

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
        hasTagManager.deleteTag(this);
        tagRepository.delete(tagID.toString());
    }

    public void saveState() {
        tagRepository.update(this);
    }

    @Override
    public String toString() {
        return tagID.toString();
    }
}
