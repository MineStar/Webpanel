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

import de.minestar.Webpanel.web.WebService;

public class Webpanel {

    private WebService webservice;

    public static void main(String[] args) throws IOException {
        Webpanel webpanel = new Webpanel();

        System.out.println("ENTER TO STOP SERVICE!");
        System.in.read();

        webpanel.stop();
        System.out.println("Service stopped!");
    }

    public Webpanel() throws IOException {
        this("http://localhost", 8000, "");
    }

    public Webpanel(String host) throws IOException {
        this(host, 8000, "");
    }

    public Webpanel(String host, int port) throws IOException {
        this(host, port, "");
    }

    public Webpanel(String host, int port, String folder) throws IOException {
        if (folder.length() < 1) {
            folder = ".";
        }
        this.webservice = new WebService(host, port, folder);
        this.webservice.start();
    }

    public boolean isRunning() {
        return this.webservice != null;
    }

    public void stop() {
        if (this.isRunning()) {
            this.webservice.stop();
        }
    }
}
