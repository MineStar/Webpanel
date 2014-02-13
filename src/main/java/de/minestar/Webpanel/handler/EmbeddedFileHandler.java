package de.minestar.Webpanel.handler;

import java.util.concurrent.ConcurrentHashMap;

import de.minestar.Webpanel.units.EmbeddedFile;

public class EmbeddedFileHandler {

    private static ConcurrentHashMap<String, EmbeddedFile> fileList = new ConcurrentHashMap<String, EmbeddedFile>();

    public static String getEmbeddedFileContents(String fileName) {
        EmbeddedFile embeddedFile = fileList.get(fileName);
        if (embeddedFile == null) {
            embeddedFile = new EmbeddedFile(fileName);
        }

        if (embeddedFile.isFileValid()) {
            embeddedFile.reloadIfNeeded();
            return embeddedFile.getString();
        } else {
            return "{FILE:" + fileName + "}";
        }
    }

    public static boolean hasEmbedFile(String fileName) {
        return fileList.containsKey(fileName);
    }
}
