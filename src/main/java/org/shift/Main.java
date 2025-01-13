package org.shift;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
        // Создаем объект для хранения опций командной строки
        Options options = new Options();

        // Добавляем доступные опции командной строки
        options.addOption("o", "output", true, "Путь к выходной директории");
        options.addOption("p", "prefix", true, "Префикс для выходных файлов");
        options.addOption("s", "brief-stats", false, "Выводить краткую статистику");
        options.addOption("f", "full-stats", false, "Выводить полную статистику");
        options.addOption("h", "help", false, "Показать справку");

        // Создаем парсер для обработки аргументов
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            // Парсим аргументы командной строки
            cmd = parser.parse(options, args);

            // Если запрашивается справка, выводим ее и завершаем выполнение
            if (cmd.hasOption("h")) {
                printHelp(options);
                return;
            }

            // Получаем значения опций или задаем значения по умолчанию
            String outputPath = cmd.getOptionValue("o", "output");
            String prefix = cmd.getOptionValue("p", "");
            boolean briefStats = cmd.hasOption("s");
            boolean fullStats = cmd.hasOption("f");

            // Проверяем, что хотя бы один файл был передан для обработки
            if (cmd.getArgs().length == 0) {
                System.err.println("Ошибка: не указаны файлы для обработки.");
                printHelp(options);
                return;
            }

            String[] files = cmd.getArgs();

            // Создаем процессор данных с указанными параметрами
            DataProcessor processor = new DataProcessor(outputPath, prefix, false, briefStats, fullStats);
            processor.processFiles(files);

        } catch (ParseException e) {
            // Если произошла ошибка при парсинге аргументов, выводим сообщение и справку
            System.err.println("Ошибка при парсинге аргументов: " + e.getMessage());
            printHelp(options);
        }
    }

    // Метод для вывода справки по использованию программы
    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar ваша_программа.jar", options);
    }
}
