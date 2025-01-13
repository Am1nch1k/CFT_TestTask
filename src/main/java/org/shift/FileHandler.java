package org.shift;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

    private final BufferedWriter integerWriter;
    private final BufferedWriter floatWriter;
    private final BufferedWriter stringWriter;

    public FileHandler(String outputPath, String prefix, boolean append) throws IOException {
        Path outputDirPath = Paths.get(outputPath);


        if (!Files.exists(outputDirPath)) {
            Files.createDirectories(outputDirPath);
        }

        // Проверка прав на запись
        if (!Files.isWritable(outputDirPath)) {
            throw new IOException("Невозможно записать в директорию: " + outputDirPath.toAbsolutePath());
        }

        // Создание потоков для записи в файлы
        this.integerWriter = new BufferedWriter(new FileWriter(
                outputDirPath.resolve(prefix + "integers.txt").toFile(), append));
        this.floatWriter = new BufferedWriter(new FileWriter(
                outputDirPath.resolve(prefix + "floats.txt").toFile(), append));
        this.stringWriter = new BufferedWriter(new FileWriter(
                outputDirPath.resolve(prefix + "strings.txt").toFile(), append));
    }

    // Записываем данные в соответствующие файлы
    public void writeData(Object data) throws IOException {
        if (data instanceof Integer || data instanceof Long) {
            integerWriter.write(data.toString());
            integerWriter.newLine();
        } else if (data instanceof Double) {
            floatWriter.write(data.toString());
            floatWriter.newLine();
        } else if (data instanceof String) {
            stringWriter.write(data.toString());
            stringWriter.newLine();
        }
    }

    public void close() throws IOException {
        integerWriter.close();
        floatWriter.close();
        stringWriter.close();
    }
}
