package test_data;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class DataObjectBuilder {
    public static <T> T buildDataObject(String filePath, Class<T> dataType){
        T returnedData;
        String fullFilePath = System.getProperty("user.dir").concat(filePath);

        try (
                Reader reader = Files.newBufferedReader(Paths.get(fullFilePath));
        ) {
            Gson gson = new Gson();
            returnedData = gson.fromJson(reader, dataType);
        } catch (NoSuchFileException noSuchFileException) {
            throw new RuntimeException("[ERR] Can't locate the file: " + fullFilePath);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage());
        }
        return returnedData;
    }
}
