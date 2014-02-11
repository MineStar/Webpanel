package de.minestar.Webpanel.utils;

public class Helper {

    public static String StringToDropDown(String elementName, String text, String delimiter) {
        String response = "<select name='" + elementName + "'>";
        String[] split = text.split(delimiter);
        for (String single : split) {
            if (split.length > 0) {
                response += "<option value='" + single + "'>" + single + "</option>";
            }
        }
        response += "</select>";
        return response;
    }
}
