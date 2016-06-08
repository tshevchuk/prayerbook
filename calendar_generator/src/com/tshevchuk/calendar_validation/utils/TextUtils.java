package com.tshevchuk.calendar_validation.utils;

/**
 * Created by taras on 09.05.16.
 */
public class TextUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String simplifyText(String str) {
        if (str != null) {
            int len;
            do {
                len = str.length();
                str = str
                        .trim()
                        .replaceAll(" </", "</")
                        .replaceAll("\n", "")
                        .replaceAll("  ", " ")
                        .replaceAll("<b> <r>", "<b><r>")
                        .replaceAll("<i> <r>", "<i><r>")
                        .replaceAll("<r> ", "<r>")
                        .replaceAll("<r>,", "<r>")
                        .replaceAll("> ⊕", ">⊕")
                        .replaceAll(" , ", ", ")
                        .replaceAll(" <wbr>", "")
                        .replaceAll("<br>", ", ")
                        .replaceAll("\u00AD", "");
                if (str.endsWith(",")) {
                    str = str.substring(0, str.length() - 1);
                }
            } while (str.length() < len);
        }
        return str;
    }

    public static String join(String separator, String... items){
        StringBuilder sb = new StringBuilder();
        for(String item : items){
            if(isEmpty(item)){
                continue;
            }
            if(sb.length() > 0){
                sb.append(separator);
            }
            sb.append(item);
        }
        return sb.toString();
    }
}
