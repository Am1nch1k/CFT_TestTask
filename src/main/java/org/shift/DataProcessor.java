package org.shift;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DataProcessor {

    private String outputPath;
    private String prefix;
    private boolean append;
    private boolean briefStats;
    private boolean fullStats;

    public DataProcessor(String outputPath, String prefix, boolean append, boolean briefStats, boolean fullStats) {
        this.outputPath = outputPath;
        this.prefix = prefix;
        this.append = append;
        this.briefStats = briefStats;
        this.fullStats = fullStats;

        createOutputDirectory();
    }

    private void createOutputDirectory() {
        File outputDir = new File(outputPath);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new RuntimeException("Не удалось создать директорию для выходных файлов.");
        }
    }

    public void processFiles(String[] files) {
        List<BigInteger> integers = new ArrayList<>();
        List<BigDecimal> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String file : files) {
            try {
                processFile(file, integers, floats, strings);
            } catch (IOException e) {
                System.err.println("Ошибка чтения файла " + file + ": " + e.getMessage());
            }
        }

        
        Collections.sort(integers);
        Collections.sort(floats);
        Collections.sort(strings);

        writeToFile("integers.txt", integers);
        writeToFile("floats.txt", floats);
        writeToFile("strings.txt", strings);

        if (briefStats) {
            printBriefStats(integers.size(), floats.size(), strings.size());
        }
        if (fullStats) {
            printFullStats(integers, floats, strings);
        }
    }

    private void processFile(String file, List<BigInteger> integers, List<BigDecimal> floats, List<String> strings) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();  
                if (line.isEmpty()) {
                    continue;  
                }

                if (DataTypeChecker.isInteger(line)) {
                    integers.add(new BigInteger(line));
                } else if (DataTypeChecker.isFloat(line)) {
                    floats.add(new BigDecimal(line));
                } else {
                    strings.add(line);
                }
            }
        }
    }

    private void writeToFile(String fileName, List<?> data) {
        String fullPath = outputPath + "/" + prefix + fileName;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fullPath), StandardCharsets.UTF_8)) {
            for (Object item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл " + fileName + ": " + e.getMessage());
        }
    }

    private void printBriefStats(int integersCount, int floatsCount, int stringsCount) {
        System.out.println("Краткая статистика:");
        System.out.println("Целых чисел: " + integersCount);
        System.out.println("Чисел с плавающей запятой: " + floatsCount);
        System.out.println("Строк: " + stringsCount);
    }

    private void printFullStats(List<BigInteger> integers, List<BigDecimal> floats, List<String> strings) {
        System.out.println("Полная статистика:");

       
        System.out.println("Целых чисел: " + integers.size());
        System.out.println("Минимальное целое число: " + (integers.isEmpty() ? "Нет данных" : integers.get(0)));
        System.out.println("Максимальное целое число: " + (integers.isEmpty() ? "Нет данных" : integers.get(integers.size() - 1)));

        if (!integers.isEmpty()) {
            BigInteger sum = BigInteger.ZERO;
            for (BigInteger num : integers) {
                sum = sum.add(num);
            }
            BigDecimal avg = new BigDecimal(sum).divide(new BigDecimal(integers.size()), 2, BigDecimal.ROUND_HALF_UP); 
            System.out.println("Сумма: " + sum);
            System.out.println("Среднее значение: " + avg.setScale(2, BigDecimal.ROUND_HALF_UP));  
        }

        
        System.out.println("Чисел с плавающей запятой: " + floats.size());
        System.out.println("Минимальное число с плавающей запятой: " + (floats.isEmpty() ? "Нет данных" : floats.get(0).setScale(10, BigDecimal.ROUND_HALF_UP)));
        System.out.println("Максимальное число с плавающей запятой: " + (floats.isEmpty() ? "Нет данных" : floats.get(floats.size() - 1).setScale(6, BigDecimal.ROUND_HALF_UP)));

        if (!floats.isEmpty()) {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal num : floats) {
                sum = sum.add(num);
            }
            BigDecimal avg = sum.divide(new BigDecimal(floats.size()), 2, BigDecimal.ROUND_HALF_UP);  
            System.out.println("Сумма: " + sum.setScale(2, BigDecimal.ROUND_HALF_UP));  
            System.out.println("Среднее значение: " + avg.setScale(2, BigDecimal.ROUND_HALF_UP));  
        }

        
        System.out.println("Строк: " + strings.size());

        if (!strings.isEmpty()) {
            
            String minLengthString = Collections.min(strings, Comparator.comparingInt(String::length));
            String maxLengthString = Collections.max(strings, Comparator.comparingInt(String::length));

          
            System.out.println("Самая короткая строка: \"" + minLengthString + "\" (Длина: " + minLengthString.length() + ")");
            System.out.println("Самая длинная строка: \"" + maxLengthString + "\" (Длина: " + maxLengthString.length() + ")");
        } else {
            System.out.println("Самая короткая строка: Нет данных");
            System.out.println("Самая длинная строка: Нет данных");
        }
    }
}
