package de.minestar.Webpanel.template;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import de.minestar.Webpanel.core.WebpanelSettings;
import de.minestar.Webpanel.handler.EmbeddedFileHandler;
import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;

public class Template {
    protected final String name;
    protected String string;

    private static Template emptyTemplate = new Template();

    public static Template emptyTemplate() {
        return emptyTemplate;
    }

    protected Template() {
        this.name = "EMPTY";
        this.string = "";
    }

    public Template(String name, String templateFile) {
        this.name = name;
        this.string = this.loadTemplate(templateFile);
    }

    public String compile(UserData userData, TemplateReplacement... args) {
        String answer = this.compile(userData);
        for (TemplateReplacement arg : args) {
            answer = answer.replaceAll(arg.getName(), arg.getValue());
        }
        return answer;
    }

    public String compile(UserData userData) {
        return compile(this, userData, 0);
    }

    private String compile(Template template, UserData userData, int depth) {
        // maximum depth to avoid infinite Template-loops
        if (depth > 10) {
            return template.getString();
        }
        String text = template.getString();
        text = searchEmbeddedFiles(text);
        text = replaceUserLevels(text);
        text = searchTemplates(text, userData, depth);
        return text;
    }

    private String replaceUserLevels(String text) {
        for (UserLevel level : UserLevel.values()) {
            text = text.replaceAll("\\[USERLEVEL:" + level.name() + "\\]", level.getLevel() + "");
        }
        return text;
    }

    private String searchEmbeddedFiles(String text) {
        String split[] = text.split("\\{FILE:");

        // handle only, if there is at least one inherited template
        if (split.length < 2) {
            return text;
        }

        text = split[0];
        for (int index = 1; index < split.length; index++) {
            // search for "}"
            int endIndex = split[index].indexOf("}");
            if (endIndex <= 0) {
                continue;
            }

            // retrieve the fileName
            String fileName = split[index].substring(0, endIndex);

            // append texts
            text += EmbeddedFileHandler.getEmbeddedFileContents(fileName);
            text += split[index].substring(endIndex + 1);
        }
        return text;
    }

    private String searchTemplates(String text, UserData userData, int depth) {
        String split[] = text.split("\\{TEMPLATE:");

        // handle only, if there is at least one inherited template
        if (split.length < 2) {
            return text;
        }

        text = split[0];
        for (int index = 1; index < split.length; index++) {
            // search for "}"
            int endIndex = split[index].indexOf("}");
            if (endIndex <= 0) {
                continue;
            }

            // get templateName, minimumUserlevel, switchMode
            String templateName = split[index].substring(0, endIndex);
            String[] levelSplit = templateName.split(":");
            int minimumLevel = -1;
            int switchMode = 0;

            // {TEMPLATE:templateName[:MODE&LEVEL]} , where MODE is "=", "<",
            // ">"
            // example: {TEMPLATE:index:>40}
            if (levelSplit.length == 2) {
                try {
                    templateName = levelSplit[0];
                    int equalIndexSmaller = levelSplit[1].indexOf("<");
                    int equalIndexEqual = levelSplit[1].indexOf("=");
                    int equalIndexLarger = levelSplit[1].indexOf(">");
                    boolean noSwitch = true;
                    if (equalIndexSmaller == 0) {
                        switchMode = 1;
                        noSwitch = false;
                    }
                    if (equalIndexEqual == 0) {
                        switchMode = 2;
                        noSwitch = false;
                    }
                    if (equalIndexLarger == 0) {
                        switchMode = 3;
                        noSwitch = false;
                    }
                    if (noSwitch) {
                        minimumLevel = Integer.valueOf(levelSplit[1]);
                    } else {
                        minimumLevel = Integer.valueOf(levelSplit[1].substring(1));
                    }
                } catch (Exception e) {
                    templateName = split[index].substring(0, endIndex);
                    minimumLevel = -1;
                }
            }

            // replace the template, if the level is reached
            if ((switchMode == 0 && userData.getLevel() >= minimumLevel) || (switchMode == 1 && userData.getLevel() < minimumLevel) || (switchMode == 2 && userData.getLevel() == minimumLevel) || (switchMode == 3 && userData.getLevel() > minimumLevel)) {
                Template inherited = TemplateHandler.getTemplate(templateName);
                // Template invalid, so ignore it...
                if (inherited == null) {
                    continue;
                }
                // append texts
                text += compile(inherited, userData, (depth + 1));
                text += split[index].substring(endIndex + 1);
            } else {
                text += split[index].substring(endIndex + 1);
            }
        }
        return text;
    }

    private final String loadTemplate(String templateFile) {
        if (templateFile.startsWith("/")) {
            templateFile = templateFile.replaceFirst("/", "");
        }
        
        File templateFolder = WebpanelSettings.instance().getTemplateFolder();
        File file = new File(templateFolder, templateFile);
        if (!file.exists()) {
            System.out.println("[ WARNING ] Template '" + file + "' not found!");
            return "<b>TEMPLATE '" + file + "' NOT FOUND!</b>";
        }
        try {
            return com.google.common.io.Files.toString(file, Charset.forName("ISO-8859-1"));
        } catch (IOException e) {
            return "<b>TEMPLATE '" + file + "' NOT LOADED!</b><br/><br/>Error:<br/>" + e.getMessage();
        }
    }

    public String getName() {
        return name;
    }

    public String getString() {
        return string;
    }
}
