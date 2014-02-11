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
import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.pagehandler.main.CustomPageHandler;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class KickPageHandler extends CustomPageHandler {

    private final TemplateReplacement rpl_topic, rpl_message;

    public KickPageHandler() {
        super(true, TemplateHandler.getTemplate("action_success"));
        this.rpl_topic = new TemplateReplacement("TOPIC", "AdminStuff - /kick");
        this.rpl_message = new TemplateReplacement("MESSAGE");
    }

    @Override
    public String handle(HttpExchange http, Map<String, String> params) throws LoginInvalidException {
        super.updateReplacements(http);

        String userName = params.get("player");
        if (userName != null) {
            Server server = Bukkit.getServer();
            if (server != null) {

                Player player = PlayerUtils.getOnlinePlayer(userName);
                if (player != null) {
                    // kick player
                    String reason = params.get("reason");
                    if (reason == null) {
                        server.dispatchCommand(server.getConsoleSender(), "kick " + player.getName());
                    } else {
                        server.dispatchCommand(server.getConsoleSender(), "kick " + player.getName() + " " + reason);
                    }

                    // set info
                    this.template = TemplateHandler.getTemplate("action_success");
                    this.rpl_topic.setValue("Spieler gekickt!");
                    this.rpl_message.setValue("Spieler '" + userName + "' wurde gekickt.");
                } else {
                    this.template = TemplateHandler.getTemplate("action_error");
                    this.rpl_topic.setValue("Spieler nicht online!");
                    this.rpl_message.setValue("'" + userName + "' konnte nicht gekickt werden, weil er nicht online ist.");
                }
            } else {
                this.template = TemplateHandler.getTemplate("action_error");
                this.rpl_topic.setValue("Server nicht gefunden!");
                this.rpl_message.setValue("Spieler '" + userName + "' konnte nicht gekickt werden.");
            }
        } else {
            this.template = TemplateHandler.getTemplate("action_error");
            this.rpl_topic.setValue("Kein Spieler angegeben!");
            this.rpl_message.setValue("Du musst einen Spielernamen angeben.");
        }

        return this.template.compile(this.rpl_user, this.rpl_token, this.rpl_topic, this.rpl_message);
    }
}
