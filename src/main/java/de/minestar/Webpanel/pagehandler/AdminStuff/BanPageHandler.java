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

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.core.CommandQueue;
import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.pagehandler.main.CustomPageHandler;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;

public class BanPageHandler extends CustomPageHandler {

    private final TemplateReplacement rpl_topic, rpl_message;

    public BanPageHandler() {
        super(true, UserLevel.ADMIN.getLevel(), TemplateHandler.getTemplate("action_normal"));
        this.rpl_topic = new TemplateReplacement("TOPIC");
        this.rpl_message = new TemplateReplacement("MESSAGE");
    }

    @Override
    public String handle(HttpExchange http, Map<String, String> params, UserData userData) throws LoginInvalidException {
        super.updateReplacements(http);

        String playerName = params.get("player");
        if (playerName != null) {
            playerName = playerName.replace(" ", "").trim();
            // get reason
            String reason = params.get("reason");
            if (reason != null) {
                reason = reason.trim();
            }
            if (reason == null || reason.length() < 1) {
                this.template = TemplateHandler.getTemplate("action_error");
                this.rpl_topic.setValue("Kein Grund angegeben!");
                this.rpl_message.setValue("Du musst einen Grund angeben.");
            } else {
                CommandQueue.queue("ban " + playerName + " " + reason);
                // set info
                this.template = TemplateHandler.getTemplate("action_success");
                this.rpl_topic.setValue("Spieler gebannt!");
                this.rpl_message.setValue("Spieler '" + playerName + "' wurde gebannt (Grund: '" + reason + "').");
            }
        } else {
            this.template = TemplateHandler.getTemplate("action_error");
            this.rpl_topic.setValue("Kein Spieler angegeben!");
            this.rpl_message.setValue("Du musst einen Spielernamen angeben.");
        }

        return this.template.compile(userData, this.rpl_user, this.rpl_token, this.rpl_topic, this.rpl_message);
    }
}
