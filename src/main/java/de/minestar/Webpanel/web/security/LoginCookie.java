package de.minestar.Webpanel.web.security;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

public class LoginCookie extends Cookie {

    private String userName;
    private String token;

    public final static String COOKIE_NAME = "login";
    private final static char SEPARATOR = '|';

    public LoginCookie(String value) {
        super(COOKIE_NAME, value);
        this.extractAuthentifaction(value);
    }

    public LoginCookie(String userName, String token) {
        super(COOKIE_NAME, concat(userName, token));
        this.userName = userName;
        this.token = token;
    }

    private void extractAuthentifaction(String value) {
        int separatorIndex = value.indexOf(SEPARATOR);
        this.userName = value.substring(0, separatorIndex);
        this.token = value.substring(separatorIndex + 1);
    }

    private static String concat(String userName, String token) {
        return userName + SEPARATOR + token;
    }

    public String getToken() {
        return token;
    }

    public String getUserName() {
        return userName;
    }

    private final static int maxAge = (int) TimeUnit.HOURS.toSeconds(1);

    public static NewCookie createNew(String userName, String token) {
        return new NewCookie(new LoginCookie(userName, token), "", maxAge, false);
    }

    public static NewCookie delete(LoginCookie cookie) {
        return new NewCookie(cookie, "", 0, false);
    }

    @Override
    public String toString() {
        return "LoginCookie={UserName: " + userName + ";Token:" + token + "}";
    }
}
