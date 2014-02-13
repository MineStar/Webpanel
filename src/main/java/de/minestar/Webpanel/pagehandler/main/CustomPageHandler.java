package de.minestar.Webpanel.pagehandler.main;

import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import de.minestar.Webpanel.exceptions.LoginInvalidException;
import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.pagehandler.AbstractHTMLHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.template.TemplateReplacement;
import de.minestar.Webpanel.units.UserData;

public abstract class CustomPageHandler extends AbstractHTMLHandler {

    protected Template template;
    protected final TemplateReplacement rpl_user, rpl_token;

    protected CustomPageHandler(boolean needsLogin, int neededLevel) {
        this(needsLogin, neededLevel, Template.emptyTemplate());
    }

    protected CustomPageHandler(boolean needsLogin, int neededLevel, Template template) {
        super(needsLogin, neededLevel);

        // set template
        this.template = template;

        // create replacements
        this.rpl_user = new TemplateReplacement("USERNAME");
        this.rpl_token = new TemplateReplacement("TOKEN");
    }

    @SuppressWarnings("unchecked")
    protected final void updateReplacements(HttpExchange http) throws LoginInvalidException {
        if (this.needsLogin()) {
            Map<String, String> params = (Map<String, String>) http.getAttribute("parameters");
            String userName = params.get("username");
            String token = params.get("token");

            if (userName != null && token != null && AuthHandler.isUserLoginValid(userName, token)) {
                // get userdata
                UserData user = AuthHandler.getUser(userName);

                // update replacements...
                this.rpl_user.setValue(userName);
                this.rpl_token.setValue(user.getToken());
            } else {
                throw new LoginInvalidException();
            }
        }
    }

}
