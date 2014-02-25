package de.minestar.Webpanel.core;

import org.bukkit.Bukkit;

import de.minestar.minestarlibrary.AbstractCore;

public class WebpanelCore extends AbstractCore {

    private Webpanel webpanel;

    public WebpanelCore() {
        super("Webpanel");
    }

    @Override
    protected boolean commonEnable() {
        try {
            // load the settings
            new WebpanelSettings("plugins/Webpanel");
            this.webpanel = new Webpanel("http://localhost", 8000, "plugins/Webpanel/");
            Bukkit.getServer().getScheduler().runTaskTimer(this, new CommandQueue(), 20, 2);
            return true;
        } catch (Exception e) {
            this.webpanel = null;
            return false;
        }
    }

    @Override
    protected boolean commonDisable() {
        if (this.webpanel != null) {
            this.webpanel.stop();
        }
        return true;
    }

}
