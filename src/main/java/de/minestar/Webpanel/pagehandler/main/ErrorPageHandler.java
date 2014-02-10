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

package de.minestar.Webpanel.pagehandler.main;

import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.units.AuthHandler;
import de.minestar.Webpanel.units.UserData;

public class ErrorPageHandler extends CustomPageHandler {

    public ErrorPageHandler() {
        super(false, TemplateHandler.getTemplate("error404"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public String handle(HttpExchange http) {
        Map<String, String> params = (Map<String, String>) http.getAttribute("parameters");
        String userName = params.get("username");
        String token = params.get("token");

        if (userName != null && token != null && AuthHandler.isUserLoginValid(userName, token)) {
            // get userdata
            UserData user = AuthHandler.getUser(userName);

            // update replacements...
            this.rpl_user.setValue(userName);
            this.rpl_token.setValue(user.getToken());
            this.rpl_navigation.setValue(TemplateHandler.getTemplate("tpl_navi_on").getString());
        } else {
            this.rpl_navigation.setValue(TemplateHandler.getTemplate("tpl_navi_off").getString());
        }
        return this.template.autoReplace(this.rpl_navigation, this.rpl_user, this.rpl_token);

    }
}
