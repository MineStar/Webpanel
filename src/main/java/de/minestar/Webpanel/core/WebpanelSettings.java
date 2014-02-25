package de.minestar.Webpanel.core;

import java.io.File;

public class WebpanelSettings {

    private static WebpanelSettings instance;

    public static WebpanelSettings instance() {
        return instance;
    }

    public WebpanelSettings(String pluginFolder) {

        instance = this;

        this.pluginFolder = new File(pluginFolder);
        this.templateFolder = new File(pluginFolder, "templates");
        this.webFolder = new File(pluginFolder, "web");
        this.embeddedFilesFolder = new File(pluginFolder, "embeddedFiles");
        this.packageFolder = "de.minestar.Webpanel.web";
    }

    private File pluginFolder;
    private File webFolder;
    private File templateFolder;
    private File embeddedFilesFolder;
    private String packageFolder;

    public String getPackageFolder() {
        return packageFolder;
    }

    public File getPluginFolder() {
        return pluginFolder;
    }

    public File getWebFolder() {
        return webFolder;
    }

    public File getTemplateFolder() {
        return templateFolder;
    }

    public File getEmbeddedFilesFolder() {
        return embeddedFilesFolder;
    }

}
