package de.minestar.Webpanel.web.security;

import de.minestar.Webpanel.handler.AuthHandler;
import de.minestar.Webpanel.units.UserData;
import de.minestar.Webpanel.units.UserLevel;
import de.minestar.Webpanel.web.exception.ForbiddenException;
import de.minestar.Webpanel.web.exception.UnauthorizedException;

/**
 * Facade class for {@link AuthHandler}. This class will replace the current
 * authhandler when the rewrite is finished
 */
public class NewAuthHandler {

    /**
     * Check if the cookie is valid and its containing credentials also valid.
     * The check doesn't include whether the user is allowed the use the
     * resource. For a level based check, see
     * {@link #check(LoginCookie, UserLevel)} <br>
     * Use this method for general access
     * 
     * @param cookie
     *            The cookie to check. <code>null</code> will throw a
     *            {@link UnauthorizedException}
     * 
     * @return The user data of the user. Useful for the templates
     * 
     * @throws UnauthorizedException
     *             When the cookie isn't set/expired or the credentials are
     *             wrong
     * @throws ForbiddenException
     *             When the user want to access a resource he hasn't the
     *             permission level for.
     */
    public static UserData check(LoginCookie cookie) throws UnauthorizedException, ForbiddenException {
        return check(cookie, UserLevel.DEFAULT);
    }

    /**
     * Check if the cookie is valid and its containing credentials also valid.
     * Also checks, if the user with his level is allowed to use the resource or
     * not.
     * 
     * @param cookie
     *            The cookie to check. <code>null</code> will throw a
     *            {@link UnauthorizedException}
     * @param level
     *            The level the user needs at least to access the resource. When
     *            to low, a {@link ForbiddenException} is thrown
     * 
     * @return The user data of the user. Useful for the templates
     * 
     * @throws UnauthorizedException
     *             When the cookie isn't set/expired or the credentials are
     *             wrong
     * @throws ForbiddenException
     *             When the user want to access a resource he hasn't the
     *             permission level for
     */
    public static UserData check(LoginCookie cookie, UserLevel level) throws UnauthorizedException, ForbiddenException {

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
