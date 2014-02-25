package de.minestar.Webpanel.services;

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
    }
}
