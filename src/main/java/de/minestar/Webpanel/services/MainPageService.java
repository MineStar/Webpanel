package de.minestar.Webpanel.services;

public class MainPageService extends RegisterService {

    @Override
    protected void registerTemplates() {
        // register main-templates
        this.registerTemplate("error404", "/main/error404.html");
        this.registerTemplate("login", "/main/login.html");
        this.registerTemplate("logout", "/main/logout.html");
        this.registerTemplate("doLogin", "/main/doLogin.html");
        this.registerTemplate("invalidLogin", "/main/invalidLogin.html");
        this.registerTemplate("insufficentRights", "/main/insufficentRights.html");
    }

    @Override
    protected void registerPages() {
        // register main-pages
    }
}
