package de.minestar.Webpanel.template;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Template {
    protected final String name;
    protected final String string;

    private static Template emptyTemplate = new Template();

    public static Template emptyTemplate() {
        return emptyTemplate;
    }

    private Template() {
        this.name = "EMPTY";
        this.string = "";
    }

    public Template(String name, String templateFile) {
        this.name = name;
        this.string = this.loadTemplate(templateFile);
    }

    public String compile(TemplateReplacement... args) {
        String answer = this.compile();
        for (TemplateReplacement arg : args) {
            answer = answer.replaceAll(arg.getName(), arg.getValue());
        }
        return answer;
    }

    public String compile() {
        return compile(this, 0);
    }

    private String compile(Template template, int depth) {
        // maximum depth to avoid infinite Template-loops
        if (depth > 10) {
            return template.getString();
        }
        String text = template.getString();
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
            // retrieve the templateName
            String templateName = split[index].substring(0, endIndex);
            Template inherited = TemplateHandler.getTemplate(templateName);
            // Template invalid, so ignore it...
            if (inherited == null) {
                continue;
            }
            // append texts
            text += compile(inherited, (depth + 1));
            text += split[index].substring(endIndex + 1);
        }
        return text;
    }

    private final String loadTemplate(String templateFile) {
        if (templateFile.startsWith("/")) {
            templateFile = templateFile.replaceFirst("/", "");
        }
        File file = new File("templates/" + templateFile);

        if (!file.exists()) {
            return "<b>TEMPLATE '" + templateFile + "' NOT FOUND!</b>";
        }
        try {
            return com.google.common.io.Files.toString(file, Charset.forName("ISO-8859-1"));
        } catch (IOException e) {
            return "<b>TEMPLATE '" + templateFile + "' NOT LOADED!</b><br/><br/>Error:<br/>" + e.getMessage();
        }
    }

    public String getName() {
        return name;
    }

    public String getString() {
        return string;
    }

}
