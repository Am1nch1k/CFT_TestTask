package org.shift;
import java.math.BigInteger;
import java.math.BigDecimal;
public class DataTypeChecker {

    public static boolean isInteger(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String value) {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
