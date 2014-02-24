package de.minestar.Webpanel.web.security;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;
import de.minestar.Webpanel.web.LoginCookie;
import de.minestar.Webpanel.web.exception.ForbiddenException;
import de.minestar.Webpanel.web.exception.UnauthorizedException;

public class NewAuthHandler {

    public static UserData check(LoginCookie cookie) {
        return check(cookie, UserLevel.DEFAULT);
    }

    public static UserData check(LoginCookie cookie, UserLevel level) {
        if (cookie == null) {
            throw new UnauthorizedException();
        }
        if (!AuthHandler.isUserLoginValid(cookie.getUserName(), cookie.getToken())) {
            throw new UnauthorizedException();
        }

        UserData user = AuthHandler.getUser(cookie.getUserName());
        if (level.getLevel() > user.getLevel()) {
            throw new ForbiddenException(user);
        }
        return user;
    }
}
