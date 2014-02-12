package de.minestar.Webpanel.services;

public class TemplateService extends RegisterService {

    @Override
    protected void registerTemplates() {
        // register navigations
        this.registerTemplate("tpl_navi", "/main/tpl_navi.html");
        this.registerTemplate("tpl_navi_off", "/main/tpl_navi_off.html");
        this.registerTemplate("tpl_navi_on", "/main/tpl_navi_on.html");
        this.registerTemplate("tpl_navi_mods", "/main/tpl_navi_mods.html");
        this.registerTemplate("tpl_navi_admin", "/main/tpl_navi_admin.html");

        // register action-templates
        this.registerTemplate("action_success", "/Actions/success.html");
        this.registerTemplate("action_normal", "/Actions/normal.html");
        this.registerTemplate("action_error", "/Actions/error.html");
    }

    @Override
    protected void registerPages() {
    }
}
