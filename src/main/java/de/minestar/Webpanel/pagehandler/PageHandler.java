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

package de.minestar.Webpanel.pagehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.minestar.Webpanel.core.WebpanelSettings;
import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.units.HandlerList;
import de.minestar.Webpanel.units.UserData;

public class PageHandler implements HttpHandler {

    private String defaultPage = "/login.html";
    private String errorPage = "/error404.html";
    private String invalidLoginPage = "/invalidLogin.html";
    private String insufficentRights = "/insufficentRights.html";

    @SuppressWarnings("unchecked")
    public void handle(HttpExchange http) throws IOException {
        // get pagename
        String pageName = http.getRequestURI().toString();

        // cut off everything after the '?'
        int index = pageName.indexOf('?');
        if (index != -1) {
            pageName = pageName.substring(0, index);
        }

        if (pageName.length() < 2) {
            pageName = this.defaultPage;
        }

        // get the parameters
        Map<String, String> params = (Map<String, String>) http.getAttribute("parameters");
        String userName = params.get("username");
        String token = params.get("token");

        // get the current user
        UserData currentUser = AuthHandler.defaultUser;
        if (userName != null && token != null && AuthHandler.isUserLoginValid(userName, token)) {
            currentUser = AuthHandler.getUser(userName);
        }

        try {
            if (HandlerList.hasHandler(pageName)) {
                // get the handler
                AbstractHTMLHandler handler = HandlerList.getHandler(pageName);

                // construct the response...
                String response = "";
                if (handler.needsLogin()) {
                    if (userName == null || token == null || !AuthHandler.isUserLoginValid(userName, token)) {
                        // user is invalid, so display invalid loginpage...
                        AbstractHTMLHandler invalidHandler = HandlerList.getHandler(this.invalidLoginPage);
                        response = "ERROR - Login invalid!";
                        if (invalidHandler != null) {
                            response = invalidHandler.handle(http, params, currentUser);
                        }
                    } else {
                        // check if the level is high enough
                        if (currentUser.getLevel() >= handler.getNeededLevel()) {
                            // update the token
                            AuthHandler.refreshUserToken(userName);

                            // login is valid, so display...
                            response = handler.handle(http, params, AuthHandler.getUser(userName));
                        } else {
                            // user is invalid, so display invalid loginpage...
                            AbstractHTMLHandler insufficentRights = HandlerList.getHandler(this.insufficentRights);
                            response = "ERROR - Insufficent rights!";
                            if (insufficentRights != null) {
                                response = insufficentRights.handle(http, params, currentUser);
                            }
                        }
                    }
                } else {
                    try {
                        // no login needed, just display...
                        response = HandlerList.getHandler(pageName).handle(http, params, currentUser);
                    } catch (LoginInvalidException e) {
                        // this is needed only for the doLogin-page...
                        AbstractHTMLHandler invalidHandler = HandlerList.getHandler(this.invalidLoginPage);
                        response = "ERROR - Login invalid!";
                        if (invalidHandler != null) {
                            response = invalidHandler.handle(http, params, currentUser);
                        }
                    }
                }

                // write response
                http.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = http.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // try to find the file...
                String fileName = pageName;
                if (fileName.startsWith("/")) {
                    fileName = fileName.replaceFirst("/", "");
                }
                File webFolder = WebpanelSettings.instance().getWebFolder();

                File webFile = new File(webFolder, fileName);
                // if the file exists, just write it...
                if (webFile.exists() && webFile.isFile()) {
                    // handle incorrect path...
                    byte[] bytes = this.readFile(webFile);
                    if (bytes != null) {
                        http.sendResponseHeaders(200, bytes.length);
                        OutputStream os = http.getResponseBody();
                        os.write(bytes);
                        os.close();
                        // return is needed, since we don't want to display the
                        // defaultPage
                        return;
                    }
                }

                // handle 404
                AbstractHTMLHandler errorHandler = HandlerList.getHandler(this.errorPage);
                String response = "ERROR404 - Page not found!";
                if (errorHandler != null) {
                    response = errorHandler.handle(http, params, currentUser);
                }
                http.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = http.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } catch (Exception e) {
            // handle internal errors...
            e.printStackTrace();
            String response = "<b>Internal servererror!</b>";
            http.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = http.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public void setInsufficentRights(String insufficentRights) {
        this.insufficentRights = insufficentRights;
    }

    public void setInvalidLoginPage(String invalidLoginPage) {
        this.invalidLoginPage = invalidLoginPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    private byte[] readFile(final File file) {
        if (file.isDirectory())
            throw new RuntimeException("Unsupported operation, file " + file.getAbsolutePath() + " is a directory");
        if (file.length() > Integer.MAX_VALUE)
            throw new RuntimeException("Unsupported operation, file " + file.getAbsolutePath() + " is too big");

        Throwable pending = null;
        FileInputStream in = null;
        final byte buffer[] = new byte[(int) file.length()];
        try {
            in = new FileInputStream(file);
            in.read(buffer);
        } catch (Exception e) {
            pending = new RuntimeException("Exception occured on reading file " + file.getAbsolutePath(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    if (pending == null) {
                        pending = new RuntimeException("Exception occured on closing file" + file.getAbsolutePath(), e);
                    }
                }
            }
            if (pending != null) {
                throw new RuntimeException(pending);
            }
        }
        return buffer;
    }
}
