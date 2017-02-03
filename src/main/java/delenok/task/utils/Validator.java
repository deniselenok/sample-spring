package delenok.task.utils;

import delenok.task.exceptions.ValidationBasicException;

import java.math.BigDecimal;

public class Validator {
    public static void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new ValidationBasicException(message);
        }
    }

    public static void checkNotNullNotEmpty(String string, String message) {
        if (string == null || string.isEmpty()) {
            throw new ValidationBasicException(message);
        }
    }

    public static void checkIfPositive(BigDecimal bigDecimal, String message) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) >= 0) {
            throw new ValidationBasicException(message);
        }
    }
}
