package org.shift;
import java.math.BigInteger;
import java.math.BigDecimal;
public class DataTypeChecker {

    // Проверка, является ли строка целым числом
    public static boolean isInteger(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка, является ли строка числом с плавающей запятой
    public static boolean isFloat(String value) {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
