package ruinMapper.fixtures;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class AreaRepoDummy implements
        CRUDRepositoryPort<Area> {
    @Override
    public void create(Area object) {

    }

    @Override
    public Area read(String ID) {
        return null;
    }

    @Override
    public void update(Area object) {

    }

    @Override
    public void delete(String ID) {

    }
}
