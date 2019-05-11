package ruinMapper.hexagon.domain.tag;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.model.ComponentType;
import ruinMapper.hexagon.domain.model.HasRoom;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

import java.util.UUID;

//TODO Validation
public class Tag extends ComponentSuper implements TagPort,
        HasRoom {
    private String tagType;
    private CRUDRepositoryPort<Tag> tagRepository;
    private ContextPort context;
    private UUID tagID;

    public Tag(String tagType,
               ContextPort context,
               CRUDRepositoryPort<Tag> tagRepository,
               UUID tagID) {
        this.tagType = tagType;
        this.tagRepository = tagRepository;

        this.context = context;
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
        if (context != null) {
            ContextPort temp = context;
            context = null;
            temp.deleteValidTag(this);
            tagRepository.delete(tagID.toString());
        }
    }

    public void saveState() {
        tagRepository.update(this);
    }

    @Override
    public String toString() {
        return tagID.toString();
    }

    @Override
    public ComponentType getType() {
        return ComponentType.TAG;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public ContextPort getContext() {
        return context;
    }

    public void setContext(
            ContextPort context) {
        this.context = context;
    }

    public UUID getTagID() {
        return tagID;
    }

    public void setTagID(UUID tagID) {
        this.tagID = tagID;
    }
}
