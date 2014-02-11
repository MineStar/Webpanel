/*
 * Copyright (C) 2014 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.Webpanel.core;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import de.minestar.Webpanel.pagehandler.AbstractHTMLHandler;
import de.minestar.Webpanel.pagehandler.PageHandler;
import de.minestar.Webpanel.pagehandler.AdminStuff.AdminStuffPageHandler;
import de.minestar.Webpanel.pagehandler.AdminStuff.KickPageHandler;
import de.minestar.Webpanel.pagehandler.main.ChatPageHandler;
import de.minestar.Webpanel.pagehandler.main.DoLoginPageHandler;
import de.minestar.Webpanel.pagehandler.main.ErrorPageHandler;
import de.minestar.Webpanel.pagehandler.main.InvalidLoginPageHandler;
import de.minestar.Webpanel.pagehandler.main.LoginPageHandler;
import de.minestar.Webpanel.pagehandler.main.LogoutPageHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.units.AuthHandler;
import de.minestar.Webpanel.units.HandlerList;
import de.minestar.Webpanel.utils.ParameterFilter;

public class Webpanel {

    private HttpServer server;

    public Webpanel(int port) throws IOException {
        try {
            System.out.println("Starting webserver @ port: " + port);

            this.server = HttpServer.create(new InetSocketAddress(port), 0);

            // create mainHandler
            PageHandler pageHandler = new PageHandler();
            server.createContext("/", pageHandler).getFilters().add(new ParameterFilter());

            // create subHandler
            if (!AuthHandler.init()) {
                throw new Exception("AuthHandler not initialized!");
            }
            this.startUp();

            // createContext
            server.setExecutor(null);
            server.start();
            System.out.println("Webserver started @ port: " + port);
        } catch (Exception e) {
            System.out.println("ERROR: could not start server @ port: " + port);
            e.printStackTrace();
            this.server = null;
        }
    }

    private void startUp() {
        // register navigations
        this.registerTemplate("tpl_navi_on", "/main/tpl_navi_on.html");
        this.registerTemplate("tpl_navi_off", "/main/tpl_navi_off.html");

        // register main-templates
        this.registerTemplate("error404", "/main/error404.html");
        this.registerTemplate("login", "/main/login.html");
        this.registerTemplate("logout", "/main/logout.html");
        this.registerTemplate("doLogin", "/main/doLogin.html");
        this.registerTemplate("invalidLogin", "/main/invalidLogin.html");

        // register action-templates
        this.registerTemplate("action_success", "/Actions/success.html");
        this.registerTemplate("action_error", "/Actions/error.html");

        // register server-templates
        this.registerTemplate("chat", "/server/chat.html");

        // register AdminStuff-Templates
        this.registerTemplate("AdminStuff", "/AdminStuff/plugin.html");

        // register main-pages
        this.registerPage(new ErrorPageHandler(), "/error404.html");
        this.registerPage(new InvalidLoginPageHandler(), "/invalidLogin.html");
        this.registerPage(new LoginPageHandler(), "/login.html");
        this.registerPage(new LogoutPageHandler(), "/logout.html");
        this.registerPage(new DoLoginPageHandler(), "/doLogin.html");

        // register AdminStuff-Pages
        this.registerPage(new AdminStuffPageHandler(), "/AdminStuff.html");
        this.registerPage(new KickPageHandler(), "/AS_kickPlayer.html");

        // register server-pages
        this.registerPage(new ChatPageHandler(), "/chat.html");
    }

    private void registerTemplate(String name, String path) {
        TemplateHandler.addTemplate(new Template(name, path));
    }

    public void registerPage(AbstractHTMLHandler handler, String path) {
        HandlerList.registerHandler(path, handler);
    }

    public HttpServer getServer() {
        return server;
    }

    public boolean isRunning() {
        return this.server != null;
    }

    public void close() {
        if (this.server != null) {
            this.server.stop(0);
        }
    }

    public static void main(String[] args) {
        try {
            new Webpanel(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
