package ruinMapper.hexagon.infrastructure.persistence;

import java.nio.file.Paths;

public class RepositoryAdapter {
    private String directoryPath;

    public RepositoryAdapter(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    protected String createFilelocation(String itemID) {
        return Paths.get(directoryPath, itemID + ".json")
                .toString();
    }
}
