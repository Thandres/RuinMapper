package ruinMapper.hexagon.domain.tag;

import ruinMapper.hexagon.domain.ComponentSuper;
import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.context.ContextPort;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

import java.util.UUID;

//TODO Validation
public class Tag extends ComponentSuper implements TagPort {
    private String tagType;
    private CRUDRepositoryPort<Tag> tagRepository;
    private CRUDRepositoryPort<Context> contextRepository;
    private String contextID;
    private UUID tagID;

    public Tag(String tagType,
               String contextID,
               CRUDRepositoryPort<Tag> tagRepository,
               CRUDRepositoryPort<Context> contextRepository,
               UUID tagID) {
        this.tagType = tagType;
        this.tagRepository = tagRepository;

        this.contextID = contextID;
        this.contextRepository = contextRepository;
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
        if (contextID != null) {
            ContextPort temp = contextRepository
                    .read(contextID);
            contextID = null;
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

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(
            String contextID) {
        this.contextID = contextID;
    }

    public UUID getTagID() {
        return tagID;
    }

    public void setTagID(UUID tagID) {
        this.tagID = tagID;
    }
}
