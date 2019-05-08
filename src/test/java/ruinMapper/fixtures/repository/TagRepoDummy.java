package ruinMapper.fixtures.repository;

import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.domain.tag.Tag;

public class TagRepoDummy implements
        CRUDRepositoryPort<Tag> {
    @Override
    public void create(Tag object) {

    }

    @Override
    public Tag read(String ID) {
        return null;
    }

    @Override
    public void update(Tag object) {

    }

    @Override
    public void delete(String ID) {

    }
}
