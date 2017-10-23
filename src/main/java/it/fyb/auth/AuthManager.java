package it.fyb.auth;

import it.fyb.Utlis.Utils;
import it.fyb.model.RegistrationUser;
import it.fyb.model.UserProfile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthManager {

    public static Map<Integer, AuthToken> authenticated = new HashMap<>();

    public static boolean checkAuthentication(Integer userId, String token) {
        if (authenticated.containsKey(userId) &&
                authenticated.get(userId).getExpiration().getTime() >= (new Date()).getTime() &&
                authenticated.get(userId).getToken().equals(token)){
            return true;
        } else {
            if (authenticated.containsKey(userId)) {
                authenticated.remove(userId);
            }
            return false;
        }
    }

    public static AuthToken getToken(RegistrationUser user) throws Exception {
        AuthToken token = new AuthToken();
        Date expiration = new Date();
        expiration.setTime(expiration.getTime()+18000000); // 5 ore
        String randomToken = UUID.randomUUID().toString();
        Integer userId = user.getId();

        token.setToken(Utils.md5Encode(randomToken));
        token.setUserId(userId);
        token.setExpiration(expiration);

        authenticated.put(userId, token);
        return token;
    }
}
