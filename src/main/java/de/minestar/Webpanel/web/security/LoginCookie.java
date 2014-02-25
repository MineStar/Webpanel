package de.minestar.Webpanel.web.security;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Cookie to store the username and the token for his session
 */
public class LoginCookie extends Cookie {

    private String userName;
    private String token;

    public final static String COOKIE_NAME = "login";
    private final static char SEPARATOR = '|';

    /**
     * Creates the token from the content string. Used by jersey
     */
    public LoginCookie(String content) {
        super(COOKIE_NAME, content);
        this.extractAuthentifaction(content);
    }

    /**
     * Creates the Cookie and concat the information to one content string
     * 
     * @param userName
     *            The name of the user
     * @param token
     *            The token associated to the user
     */
    private LoginCookie(String userName, String token) {
        super(COOKIE_NAME, concat(userName, token));
        this.userName = userName;
        this.token = token;
    }

    /**
     * Split the content string into the username and the token
     * 
     * @param content
     *            The single content string separated by
     */
    private void extractAuthentifaction(String content) {
        int separatorIndex = content.indexOf(SEPARATOR);
        this.userName = content.substring(0, separatorIndex);
        this.token = content.substring(separatorIndex + 1);
    }

    private static String concat(String userName, String token) {
        return userName + SEPARATOR + token;
    }

    /**
     * @return The token from the cookie
     */
    public String getToken() {
        return token;
    }

    /**
     * @return The username from the cookie
     */
    public String getUserName() {
        return userName;
    }

    /**
     * TODO: Make this variable<br>
     * Life time in seconds - currently an hour
     */
    private final static int maxAge = (int) TimeUnit.HOURS.toSeconds(1);

    /**
     * Creates a new cookie used for
     * {@link ResponseBuilder#cookie(NewCookie...)}
     * 
     * @param userName
     *            The username
     * @param token
     *            The token assiociated to the user in this session
     * @return A NewCookie containing the neccessary information
     */
    public static NewCookie createNew(String userName, String token) {
        return new NewCookie(new LoginCookie(userName, token), "", maxAge, false);
    }

    /**
     * Set the cookie as expired. Used for a logout of the user in
     * {@link ResponseBuilder#cookie(NewCookie...)}
     * 
     * @param cookie
     *            The current cookie of the user
     * @return A cookie to expire
     */
    public static NewCookie delete(LoginCookie cookie) {
        return new NewCookie(cookie, "", 0, false);
    }

    @Override
    public String toString() {
        return "LoginCookie={UserName: " + userName + ";Token:" + token + "}";
    }
}
