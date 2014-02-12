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

package de.minestar.Webpanel.pagehandler.AdminStuff;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.pagehandler.main.CustomPageHandler;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.utils.Helper;

public class AdminStuffPageHandler extends CustomPageHandler {

    private TemplateReplacement rpl_playerList;

    public AdminStuffPageHandler() {
        super(true, 10, TemplateHandler.getTemplate("AdminStuff"));
        this.rpl_playerList = new TemplateReplacement("PLAYERLIST");
    }

    @Override
    public String handle(HttpExchange http, Map<String, String> params, UserData userData) throws LoginInvalidException {
        super.updateReplacements(http);
        if (Bukkit.getServer() != null) {
            this.rpl_playerList.setValue(Helper.StringToDropDown("player", this.getPlayerList(), ";", "dropdown_big"));
        } else {
            this.rpl_playerList.setValue("Server ist offline!");
        }
        return this.template.compile(userData, this.rpl_playerList, this.rpl_user, this.rpl_token);
    }

    private String getPlayerList() {
        // build response
        String response = "";
        Player[] list = Bukkit.getOnlinePlayers();
        for (Player player : list) {
            response += player.getName() + ";";
        }
        return response;
    }
}
