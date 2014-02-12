package de.minestar.Webpanel.services;

import de.minestar.Webpanel.pagehandler.AdminStuff.AdminStuffPageHandler;
import de.minestar.Webpanel.pagehandler.AdminStuff.BanPageHandler;
import de.minestar.Webpanel.pagehandler.AdminStuff.GodmodePageHandler;
import de.minestar.Webpanel.pagehandler.AdminStuff.KickPageHandler;

public class AdminStuffService extends RegisterService {

    @Override
    protected void registerTemplates() {
        // register AdminStuff-Templates
        this.registerTemplate("AdminStuff", "/AdminStuff/plugin.html");
        this.registerTemplate("as_mods", "/AdminStuff/mods.html");
        this.registerTemplate("as_admins", "/AdminStuff/admins.html");
    }

    @Override
    protected void registerPages() {
        // register AdminStuff-Pages
        this.registerPage(new AdminStuffPageHandler(), "/AdminStuff.html");
        this.registerPage(new KickPageHandler(), "/AS_kickPlayer.html");
        this.registerPage(new BanPageHandler(), "/AS_banPlayer.html");
        this.registerPage(new GodmodePageHandler(), "/AS_godmode.html");
    }
}
