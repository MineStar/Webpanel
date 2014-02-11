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

package de.minestar.Webpanel.units;

import java.util.HashMap;

import de.minestar.Webpanel.pagehandler.AbstractHTMLHandler;

public class HandlerList {
	private static HashMap<String, AbstractHTMLHandler> registeredHandlers = new HashMap<String, AbstractHTMLHandler>();

	public static void registerHandler(String path, AbstractHTMLHandler handler) {
		registeredHandlers.put(path, handler);
	}

	public static AbstractHTMLHandler getHandler(String path) {
		return registeredHandlers.get(path);
	}

	public static boolean hasHandler(String path) {
		return registeredHandlers.containsKey(path);
	}
}
