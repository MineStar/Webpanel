package de.minestar.Webpanel.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;

public class CommandQueue implements Runnable {
    private static List<String> queuedCommands = Collections.synchronizedList(new ArrayList<String>());

    public static void queue(String command) {
        queuedCommands.add(command);
    }

    public void run() {
        if (queuedCommands.isEmpty()) {
            return;
        }

        for (String command : queuedCommands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
        queuedCommands.clear();
    }

}
