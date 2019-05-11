package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

public class HintRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Hint> {
    private DtoMapper<Hint, HintDto> hintMapper;

    public HintRepository(
            DtoMapper<Hint, HintDto> hintMapper,
            String directoryPath) {
        super(directoryPath);
        this.hintMapper = hintMapper;
    }

    @Override
    public void create(Hint object) {
        HintDto hintDto = hintMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                hintDto);
    }

    @Override
    public Hint read(String ID) {
        HintDto dto = FileHelper
                .readFromFile(createFilelocation(ID),
                        HintDto.class);
        return hintMapper.toDomain(dto, this);
    }

    @Override
    public void update(Hint object) {
        HintDto hintDto = hintMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                hintDto);
    }

    @Override
    public void delete(String ID) {
        FileHelper.deleteFile(createFilelocation(ID));
    }
}
