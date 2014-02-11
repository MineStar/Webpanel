package de.minestar.Webpanel.template;

public class TemplateReplacement {
    private final String name;
    private String value;

    public TemplateReplacement(String name) {
        this(name, "");
    }

    public TemplateReplacement(String name, String value) {
        this.name = "\\{" + name.toUpperCase() + "\\}";
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
