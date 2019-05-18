package ruinMapper.hexagon.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    private static ObjectMapper jacksonMapper = new ObjectMapper();

    public static <T> void writeToFile(String pathToFile,
                                       T objectToWrite) {
        try {
            jacksonMapper.writeValue(new File(pathToFile),
                    objectToWrite);
        } catch (IOException e) {//TODO error handling
            e.printStackTrace();
        }
    }

    public static <T> T readFromFile(String pathToFile,
                                     Class<T> objectClass) {
        try {
            return jacksonMapper
                    .readValue(new File(
                                    pathToFile),
                            objectClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFile(String pathToFile) {
        File fileToDelete = new File(
                pathToFile);
        try {
            if (fileToDelete.delete()) {
                // Everything OK
            } else {
                throw new IOException();
            }
        } catch (IOException e) {//TODO errorhandling
            e.printStackTrace();
        }
    }
}
