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

import org.bukkit.entity.Player;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.core.CommandQueue;
import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.handler.TemplateHandler;
import de.minestar.Webpanel.pagehandler.main.CustomPageHandler;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;
import de.minestar.core.MinestarCore;
import de.minestar.core.units.MinestarPlayer;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class GodmodePageHandler extends CustomPageHandler {

    private final TemplateReplacement rpl_topic, rpl_message;

    public GodmodePageHandler() {
        super(true, UserLevel.ADMIN.getLevel(), TemplateHandler.getTemplate("action_normal"));
        this.rpl_topic = new TemplateReplacement("TOPIC");
        this.rpl_message = new TemplateReplacement("MESSAGE");
    }

    @Override
    public String handle(HttpExchange http, Map<String, String> params, UserData userData) throws LoginInvalidException {
        super.updateReplacements(http);

        String playerName = params.get("player");
        if (playerName != null) {
            Player player = PlayerUtils.getOnlinePlayer(playerName);
            if (player != null) {
                // queue command
                CommandQueue.queue("god " + player.getName());

                // get current godmode
                MinestarPlayer mPlayer = MinestarCore.getPlayer(player);
                Boolean isInGodMode = mPlayer.getBoolean("adminstuff.god");
                if (isInGodMode != null && isInGodMode) {
                    isInGodMode = true;
                } else {
                    isInGodMode = false;
                }

                // set info
                this.template = TemplateHandler.getTemplate("action_success");
                this.rpl_topic.setValue("Godmode");
                if (isInGodMode) {
                    this.rpl_message.setValue("'" + playerName + "' ist jetzt wieder sterblich.");
                } else {
                    this.rpl_message.setValue("'" + playerName + "' ist jetzt unsterblich.");
                }
            } else {
                this.template = TemplateHandler.getTemplate("action_error");
                this.rpl_topic.setValue("Spieler nicht online!");
                this.rpl_message.setValue("Die Aktion konnte nicht ausgeführt werden, weil '" + playerName + "' nicht online ist.");
            }
        } else {
            this.template = TemplateHandler.getTemplate("action_error");
            this.rpl_topic.setValue("Kein Spieler angegeben!");
            this.rpl_message.setValue("Du musst einen Spielernamen angeben.");
        }

        return this.template.compile(userData, this.rpl_user, this.rpl_token, this.rpl_topic, this.rpl_message);
    }
}
