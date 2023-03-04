package IO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileOperation {
    public static FileOperation fo = new FileOperation();

    public List<String> readFileToStringList(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }

    public void writeFile(String filePath, String line) throws IOException {
        FileWriter writer = new FileWriter(filePath, true);
        writer.write("\n"+line);
        writer.close();
    }
}
