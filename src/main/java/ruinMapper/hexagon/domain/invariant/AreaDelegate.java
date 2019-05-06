package ruinMapper.hexagon.domain.invariant;

import ruinMapper.hexagon.domain.area.AreaPort;
import ruinMapper.hexagon.domain.model.ComponentTag;
import ruinMapper.hexagon.domain.model.HasArea;

import java.util.HashSet;
import java.util.Set;

public class AreaDelegate implements AreaManager {
    private Set<AreaPort> areaSet;

    public AreaDelegate(
            Set<AreaPort> areaSet) {
        this.areaSet = areaSet;
    }

    //TODO create HasAreaDelegate
    @Override
    public void addArea(AreaPort value, HasArea key) {
        areaSet.add(value);
    }

    @Override // TODO never used, worth implementing?
    public void removeArea(AreaPort value, HasArea key) {
        areaSet.remove(value);
        // TODO remove all rooms referenced in value
    }

    @Override
    public Set<AreaPort> accessAreas(HasArea hasArea) {
        return new HashSet<>(areaSet);
    }

    @Override
    public <T extends ComponentTag> void deleteManagedObject(
            T managedObject) {
        // at the moment only context uses an areamanager, and without context every other class should get dereferenced by default
    }
}
