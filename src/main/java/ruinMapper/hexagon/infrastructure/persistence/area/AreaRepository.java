package ruinMapper.hexagon.infrastructure.persistence.area;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

import java.util.HashMap;
import java.util.Map;

public class AreaRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Area> {

    private DtoMapper<Area, AreaDto> areaMapper;
    private Map<String, Area> loadedAreas;

    public AreaRepository(
            DtoMapper<Area, AreaDto> areaMapper,
            String directoryPath) {
        super(directoryPath);
        this.areaMapper = areaMapper;
        loadedAreas = new HashMap<>();
    }

    @Override
    public void create(Area object) {
        loadedAreas.put(object.toString(), object);
        AreaDto areaDto = areaMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                areaDto);

    }

    @Override
    public Area read(String ID) {
        if (loadedAreas.containsKey(ID)) {
            return loadedAreas.get(ID);
        } else {
            AreaDto areaDto = FileHelper
                    .readFromFile(createFilelocation(ID),
                            AreaDto.class);
            return areaMapper.toDomain(areaDto, this);
        }
    }

    @Override
    public void update(Area object) {
        AreaDto areaDto = areaMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                areaDto);
    }

    @Override
    public void delete(String ID) {
        if (loadedAreas.containsKey(ID)) {
            loadedAreas.remove(ID);
        }
        FileHelper.deleteFile(createFilelocation(ID));
    }


}
