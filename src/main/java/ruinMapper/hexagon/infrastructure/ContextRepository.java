package ruinMapper.hexagon.infrastructure;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;

public class ContextRepository implements
        CRUDRepositoryPort<Context> {
    @Override
    public void create(Context object) {

    }

    @Override
    public Context read(String ID) {
        return null;
    }

    @Override
    public void update(Context object) {

    }

    @Override
    public void delete(String ID) {

    }
}
