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

import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.units.UserData;

public class ChatPageHandler extends CustomPageHandler {

    public ChatPageHandler() {
        super(true, 0, TemplateHandler.getTemplate("chat"));
    }

    @Override
    public String handle(HttpExchange http, Map<String, String> params, UserData userData) throws LoginInvalidException {
        super.updateReplacements(http);
        return this.template.compile(userData, this.rpl_user, this.rpl_token);
    }
}
