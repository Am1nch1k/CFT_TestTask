package org.shift;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("o", "output", true, "Путь к выходной директории");
        options.addOption("p", "prefix", true, "Префикс для выходных файлов");
        options.addOption("s", "brief-stats", false, "Выводить краткую статистику");
        options.addOption("f", "full-stats", false, "Выводить полную статистику");
        options.addOption("h", "help", false, "Показать справку");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                printHelp(options);
                return;
            }

            String outputPath = cmd.getOptionValue("o", "output");
            String prefix = cmd.getOptionValue("p", "");
            boolean briefStats = cmd.hasOption("s");
            boolean fullStats = cmd.hasOption("f");

            if (cmd.getArgs().length == 0) {
                System.err.println("Ошибка: не указаны файлы для обработки.");
                printHelp(options);
                return;
            }

            String[] files = cmd.getArgs();

            DataProcessor processor = new DataProcessor(outputPath, prefix, false, briefStats, fullStats);
            processor.processFiles(files);

        } catch (ParseException e) {
            System.err.println("Ошибка при парсинге аргументов: " + e.getMessage());
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar ваша_программа.jar", options);
    }
}
