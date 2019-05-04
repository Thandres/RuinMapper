package ruinMapper.hexagon.domain.tag;

import ruinMapper.hexagon.domain.Taggable;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

import java.util.*;

//TODO Validation
public class Tag implements TagPort {
    private String tagType;
    private CRUDRepositoryPort<Tag> tagRepository;
    private Set<Taggable> taggedObjects;
    private UUID tagID;

    public Tag(String tagType,
               CRUDRepositoryPort<Tag> tagRepository,
               Set<Taggable> taggedObjects,
               UUID tagID) {
        this.tagType = tagType;
        this.tagRepository = tagRepository;
        this.taggedObjects = taggedObjects;
        this.tagID = tagID;
    }

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
        List<Taggable> temp = new ArrayList<>(
                taggedObjects);
        taggedObjects.clear();
        temp.forEach(
                taggable -> taggable.removeTag(this));
        tagRepository.delete(tagID.toString());
    }

    @Override
    public void addTaggable(Taggable taggable) {
        if (taggedObjects.add(taggable)) {
            taggable.addTag(this);
            saveState();
        }
    }

    @Override
    public void removeTaggable(Taggable taggable) {
        if (taggedObjects.remove(taggable)) {
            taggable.removeTag(this);
            saveState();
        }
    }

    @Override
    public Set<Taggable> getTaggedObjects() {
        return new HashSet<>(taggedObjects);
    }

    private void saveState() {
        tagRepository.update(this);
    }
}
