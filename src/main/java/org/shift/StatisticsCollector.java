package org.shift;

import java.util.List;

public class StatisticsCollector {

    public static void printStatistics(List<Long> integers, List<Double> floats, List<String> strings) {
        System.out.println("     ¯\\_('_')_/¯     ");
        System.out.println("======СТАТИСТИКА======");

        printIntegerStatistics(integers);
        printFloatStatistics(floats);
        printStringStatistics(strings);
    }

    private static void printIntegerStatistics(List<Long> integers) {
        if (integers.isEmpty()) {
            System.out.println("Целые числа: 0");
            return;
        }

        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        double sum = 0;
        for (Long value : integers) {
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }
        double avg = sum / integers.size();
        System.out.println("Целые числа: " + integers.size() + ", Мин: " + min + ", Макс: " + max + ", Ср. значение: " + avg);
    }

    private static void printFloatStatistics(List<Double> floats) {
        if (floats.isEmpty()) {
            System.out.println("Вещественные числа: 0");
            return;
        }

        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        double sum = 0;
        for (Double value : floats) {
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }
        double avg = sum / floats.size();
        System.out.println("Вещественные числа: " + floats.size() + ", Мин: " + min + ", Макс: " + max + ", Ср. значение: " + avg);
    }

    private static void printStringStatistics(List<String> strings) {
        if (strings.isEmpty()) {
            System.out.println("Строки: 0");
            return;
        }

        int minLen = Integer.MAX_VALUE, maxLen = Integer.MIN_VALUE;
        for (String value : strings) {
            minLen = Math.min(minLen, value.length());
            maxLen = Math.max(maxLen, value.length());
        }
        System.out.println("Строки: " + strings.size() + ", Мин. длина: " + minLen + ", Макс. длина: " + maxLen);
    }
}
