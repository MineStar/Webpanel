package de.minestar.Webpanel.services;

import de.minestar.Webpanel.pagehandler.main.DoLoginPageHandler;
import de.minestar.Webpanel.pagehandler.main.ErrorPageHandler;
import de.minestar.Webpanel.pagehandler.main.InvalidLoginPageHandler;
import de.minestar.Webpanel.pagehandler.main.LoginPageHandler;
import de.minestar.Webpanel.pagehandler.main.LogoutPageHandler;

public class MainPageService extends RegisterService {

    @Override
    protected void registerTemplates() {
        // register main-templates
        this.registerTemplate("error404", "/main/error404.html");
        this.registerTemplate("login", "/main/login.html");
        this.registerTemplate("logout", "/main/logout.html");
        this.registerTemplate("doLogin", "/main/doLogin.html");
        this.registerTemplate("invalidLogin", "/main/invalidLogin.html");
    }

    @Override
    protected void registerPages() {
        // register main-pages
        this.registerPage(new ErrorPageHandler(), "/error404.html");
        this.registerPage(new InvalidLoginPageHandler(), "/invalidLogin.html");
        this.registerPage(new LoginPageHandler(), "/login.html");
        this.registerPage(new LogoutPageHandler(), "/logout.html");
        this.registerPage(new DoLoginPageHandler(), "/doLogin.html");
    }
}
