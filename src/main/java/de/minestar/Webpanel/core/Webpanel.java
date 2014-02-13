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

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.pagehandler.PageHandler;
import de.minestar.Webpanel.services.AdminStuffService;
import de.minestar.Webpanel.services.MainPageService;
import de.minestar.Webpanel.services.ServerService;
import de.minestar.Webpanel.services.TemplateService;
import de.minestar.Webpanel.utils.ParameterFilter;

public class Webpanel {

    public static Webpanel INSTANCE;

    private final String folder;
    private HttpServer server;

    public Webpanel(String folder, int port) throws Exception {
        try {
            Webpanel.INSTANCE = this;
            this.folder = folder;
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
            throw e;
        }
    }

    private void startUp() {
        new TemplateService();
        new MainPageService();
        new ServerService();
        new AdminStuffService();
    }

    public HttpServer getServer() {
        return server;
    }

    public boolean isRunning() {
        return this.server != null;
    }

    public void stop() {
        if (this.isRunning()) {
            this.server.stop(0);
        }
    }

    public static void main(String[] args) {
        try {
            new Webpanel("", 8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFolder() {
        return folder;
    }
}
