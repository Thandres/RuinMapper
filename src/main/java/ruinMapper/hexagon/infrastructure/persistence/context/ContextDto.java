package ruinMapper.hexagon.infrastructure.persistence.context;

import java.util.Set;

public class ContextDto {
    private String name;
    private Set<String> areas;
    private Set<String> validTags;
    private Set<String> quests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAreas() {
        return areas;
    }

    public void setAreas(Set<String> areas) {
        this.areas = areas;
    }

    public Set<String> getValidTags() {
        return validTags;
    }

    public void setValidTags(
            Set<String> validTags) {
        this.validTags = validTags;
    }

    public Set<String> getQuests() {
        return quests;
    }

    public void setQuests(Set<String> quests) {
        this.quests = quests;
    }
}
