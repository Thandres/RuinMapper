package ruinMapper.hexagon.infrastructure.persistence.area;

import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;

import java.io.File;
import java.nio.file.Paths;

public class AreaRepositoryAdapter implements
        CRUDRepositoryPort<Area> {


    private DtoMapper<Area, AreaDto> areaMapper;
    private String directoryPath;


    public AreaRepositoryAdapter(
            DtoMapper<Area, AreaDto> areaMapper,
            String directoryPath) {
        this.areaMapper = areaMapper;
        this.directoryPath = directoryPath;
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
        return areaMapper.toDomain(areaDto);
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

    private String createFilelocation(String itemID) {
        return Paths.get(directoryPath, itemID + ".json")
                .toString();
    }
}
