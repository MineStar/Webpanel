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

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.units.AuthHandler;

public class LogoutPageHandler extends CustomPageHandler {

	public LogoutPageHandler() {
		super(true, TemplateHandler.getTemplate("logout"));
	}

	@Override
	public String handle(HttpExchange http) throws LoginInvalidException {
		super.updateReplacements(http);
		AuthHandler.logoutUser(this.rpl_user.getValue());
		return this.template.compile();
	}
}
