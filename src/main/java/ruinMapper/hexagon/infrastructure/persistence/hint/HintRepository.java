package ruinMapper.hexagon.infrastructure.persistence.hint;

import ruinMapper.hexagon.domain.hint.Hint;
import ruinMapper.hexagon.domain.repository.CRUDRepositoryPort;
import ruinMapper.hexagon.infrastructure.persistence.DtoMapper;
import ruinMapper.hexagon.infrastructure.persistence.FileHelper;
import ruinMapper.hexagon.infrastructure.persistence.RepositoryAdapter;

import java.util.HashMap;
import java.util.Map;

public class HintRepository extends
        RepositoryAdapter implements
        CRUDRepositoryPort<Hint> {
    private DtoMapper<Hint, HintDto> hintMapper;

    private Map<String, Hint> loadedHints;

    public HintRepository(
            String directoryPath) {
        super(directoryPath);
        loadedHints = new HashMap<>();
    }

    @Override
    public void create(Hint object) {
        loadedHints.put(object.toString(), object);
        HintDto hintDto = hintMapper.toDto(object);
        FileHelper.writeToFile(
                createFilelocation(object.toString()),
                hintDto);
    }

    @Override
    public Hint read(String ID) {
        if (loadedHints.containsKey(ID)) {
            return loadedHints.get(ID);
        } else {
            HintDto dto = FileHelper
                    .readFromFile(createFilelocation(ID),
                            HintDto.class);

            Hint loadedHint = hintMapper
                    .toDomain(dto, this);
            loadedHints.put(ID, loadedHint);
            return loadedHint;
        }
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
        if (loadedHints.containsKey(ID)) {
            loadedHints.remove(ID);
        }
        FileHelper.deleteFile(createFilelocation(ID));
    }

    public void setHintMapper(
            DtoMapper<Hint, HintDto> hintMapper) {
        this.hintMapper = hintMapper;
    }
}
