package utils;


public class TextUtils {
    
    public static String trimText(String text) {
        Integer lastPunctuation = Math.max(
            Math.max(text.lastIndexOf("."), text.lastIndexOf("!")),
            text.lastIndexOf("?")
        );
        return text.substring(0, lastPunctuation + 1);
    }
}