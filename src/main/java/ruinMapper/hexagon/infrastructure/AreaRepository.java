package ruinMapper.hexagon.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import ruinMapper.hexagon.domain.area.Area;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.dto.AreaDto;
import ruinMapper.hexagon.infrastructure.dto.mapper.AreaMapper;

import java.io.File;
import java.io.IOException;

public class AreaRepository implements
        CRUDRepositoryPort<Area> {

    private ObjectMapper jacksonMapper;
    private AreaMapper areaMapper;
    private String fileLocation;


    public AreaRepository(AreaMapper areaMapper,
            String fileLocation) {
        this.jacksonMapper = new ObjectMapper();
        this.areaMapper = areaMapper;
        this.fileLocation = fileLocation;
    }

    @Override
    public void create(Area object) {
        AreaDto areaDto = areaMapper.toDto(object);

        try {
            jacksonMapper.writeValue(new File(fileLocation),
                    areaDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
