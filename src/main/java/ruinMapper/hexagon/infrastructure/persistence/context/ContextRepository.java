package ruinMapper.hexagon.infrastructure.persistence.context;

import ruinMapper.hexagon.domain.context.Context;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

public class ContextRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Context> {

    private DtoMapper<Context, ContextDto> contextMapper;
    private Context currentContext;

    public ContextRepository(
            String directoryPath,
            DtoMapper<Context, ContextDto> contextMapper) {
        super(directoryPath);
        this.contextMapper = contextMapper;
    }

    @Override
    public void create(Context object) {
        currentContext = object;
        ContextDto contextDto = contextMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                contextDto);
    }

    @Override
    public Context read(String ID) {
        if (currentContext.getName().equals(ID)) {
            return currentContext;
        } else {
            ContextDto areaDto = FileHelper
                    .readFromFile(createFilelocation(ID),
                            ContextDto.class);
            return contextMapper.toDomain(areaDto, this);
        }
    }

    @Override
    public void update(Context object) {
        currentContext = object;
        ContextDto contextDto = contextMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                contextDto);
    }

    @Override
    public void delete(String ID) {
        if (currentContext.getName().equals(ID)) {
            currentContext.setName("INVALID");
            FileHelper.deleteFile(createFilelocation(ID));
        }
    }
}