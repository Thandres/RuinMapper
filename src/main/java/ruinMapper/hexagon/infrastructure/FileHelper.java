package ruinMapper.hexagon.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    private static ObjectMapper jacksonMapper = new ObjectMapper();

    public static <T> void writeToFile(String pathToFile,
                                       T dto) {
        try {
            jacksonMapper.writeValue(new File(pathToFile),
                    dto);
        } catch (IOException e) {//TODO error handling
            e.printStackTrace();
        }
    }

    public static <T> T readFromFile(String pathToFile,
                                     Class<T> dtoClass) {
        try {
            return jacksonMapper
                    .readValue(new File(
                                    pathToFile),
                            dtoClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
