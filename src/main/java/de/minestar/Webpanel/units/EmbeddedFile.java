package de.minestar.Webpanel.units;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import de.minestar.Webpanel.core.WebpanelSettings;

public class EmbeddedFile {
    // 1000 * minutes * seconds
    private static final long cacheTimeInMS = 1000 * 60 * 1;

    protected final String fileName;
    protected String string;
    private long endOfLife;
    private boolean fileValid = false;

    public EmbeddedFile(String fileName) {
        this.fileName = fileName;
        this.string = this.loadFile(fileName);
        this.endOfLife = System.currentTimeMillis() + cacheTimeInMS;
    }

    public void reloadIfNeeded() {
        if (this.endOfLife < System.currentTimeMillis()) {
            this.string = this.loadFile(this.fileName);
            this.endOfLife = System.currentTimeMillis() + cacheTimeInMS;
        }
    }

    private final String loadFile(String fileName) {
        fileValid = false;
        if (fileName.startsWith("/")) {
            fileName = fileName.replaceFirst("/", "");
        }
        File embeddedFolder = WebpanelSettings.instance().getEmbeddedFilesFolder();

        File file = new File(embeddedFolder, fileName);
        if (!file.exists()) {
            fileValid = false;
            System.out.println("[ WARNING ] File '" + file + "' not found!");
            return "<b>FILE '" + file + "' NOT FOUND!</b>";
        }
        try {
            String content = com.google.common.io.Files.toString(file, Charset.forName("ISO-8859-1"));
            fileValid = true;
            return content;
        } catch (IOException e) {
            fileValid = false;
            return "<b>FILE '" + file + "' NOT LOADED!</b><br/><br/>Error:<br/>" + e.getMessage();
        }
    }

    public boolean isFileValid() {
        return fileValid;
    }

    public String getString() {
        return string;
    }
}
