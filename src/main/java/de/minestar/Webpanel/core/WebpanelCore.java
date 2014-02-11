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
            this.webpanel = new Webpanel("plugins/Webpanel/", 8000);
            Bukkit.getServer().getScheduler().runTaskTimer(this, new CommandQueue(), 5, 5);
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
