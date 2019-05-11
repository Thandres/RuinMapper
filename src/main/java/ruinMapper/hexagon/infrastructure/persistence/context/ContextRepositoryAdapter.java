package ruinMapper.hexagon.infrastructure.persistence.context;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;

import java.nio.file.Paths;

public class ContextRepositoryAdapter implements
        CRUDRepositoryPort<Context> {

    private String directoryPath;
    private DtoMapper<Context, ContextDto> contextMapper;

    public ContextRepositoryAdapter(
            String directoryPath,
            DtoMapper<Context, ContextDto> contextMapper) {
        this.directoryPath = directoryPath;
        this.contextMapper = contextMapper;
    }

    @Override
    public void create(Context object) {
        ContextDto contextDto = contextMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                contextDto);
    }

    @Override
    public Context read(String ID) {
        ContextDto areaDto = FileHelper
                .readFromFile(createFilelocation(ID),
                        ContextDto.class);
        return contextMapper.toDomain(areaDto);
    }

    @Override
    public void update(Context object) {
        ContextDto contextDto = contextMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                contextDto);
    }

    @Override
    public void delete(String ID) {
        FileHelper.deleteFile(createFilelocation(ID));
    }

    private String createFilelocation(String itemID) {
        return Paths.get(directoryPath, itemID + ".json")
                .toString();
    }
}
