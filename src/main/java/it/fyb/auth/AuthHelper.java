package it.fyb.auth;

import it.fyb.Utlis.FYBConstants;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import java.util.Map;

public class AuthHelper {
    public static boolean checkAuthentication(HttpHeaders headers) {
        Map<String, Cookie> cookies = headers.getCookies();
        // There are no cookies
        if (!cookies.containsKey(FYBConstants.USER_ID) || !cookies.containsKey(FYBConstants.USER_TOKEN)) {
            return false;
        }
        // If they exist, check for validity
        return AuthManager.checkAuthentication(
                Integer.valueOf(cookies.get(FYBConstants.USER_ID).getValue()),
                cookies.get(FYBConstants.USER_TOKEN).getValue());
    }
}
