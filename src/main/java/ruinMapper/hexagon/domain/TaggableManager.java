package ruinMapper.hexagon.domain;

import java.util.Set;

public interface TaggableManager {

    public void addTaggable(Taggable taggable);

    public void removeTaggable(Taggable taggable);

    public Set<Taggable> getTaggedObjects();
}
