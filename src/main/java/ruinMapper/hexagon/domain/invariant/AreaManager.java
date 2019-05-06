package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.model.ComponentManager;
import ruinMapper.hexagon.domain.model.HasArea;

import java.util.Set;

public interface AreaManager extends ComponentManager {
    public void addArea(AreaPort value, HasArea key);

    public void removeArea(AreaPort value, HasArea key);

    public Set<AreaPort> accessAreas(HasArea hasArea);
}
