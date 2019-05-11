package ruinMapper.hexagon.infrastructure.persistence.area;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

import java.io.File;

public class AreaRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Area> {

    private DtoMapper<Area, AreaDto> areaMapper;

    public AreaRepository(
            DtoMapper<Area, AreaDto> areaMapper,
            String directoryPath) {
        super(directoryPath);
        this.areaMapper = areaMapper;

    }

    @Override
    public void create(Area object) {
        AreaDto areaDto = areaMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                areaDto);

    }

    @Override
    public Area read(String ID) {
        AreaDto areaDto = FileHelper
                .readFromFile(createFilelocation(ID),
                        AreaDto.class);
        return areaMapper.toDomain(areaDto, this);
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
        File fileToDelete = new File(
                createFilelocation(ID));
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }


}
