package util;

public class Validation {
    public static boolean notEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isNumber(String text) {
        if (!notEmpty(text)) return false;
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInteger(String text) {
        if (!notEmpty(text)) return false;
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}