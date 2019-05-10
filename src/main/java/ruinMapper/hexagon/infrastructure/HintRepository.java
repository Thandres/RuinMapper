package ruinMapper.hexagon.infrastructure;

import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class HintRepository implements
        CRUDRepositoryPort<Hint> {
    @Override
    public void create(Hint object) {

    }

    @Override
    public Hint read(String ID) {
        return null;
    }

    @Override
    public void update(Hint object) {

    }

    @Override
    public void delete(String ID) {

    }
}
