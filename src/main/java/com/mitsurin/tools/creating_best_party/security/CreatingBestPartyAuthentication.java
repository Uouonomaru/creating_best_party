package com.mitsurin.tools.creating_best_party.security;

import com.mitsurin.tools.creating_best_party.model.UserMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public final class CreatingBestPartyAuthentication {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static Map<String, String> loggedinUserMap = new HashMap<>();

    /**
     * check authenticated
     * @param sessionId
     * @return
     */
    public static boolean isAuthenticated(String sessionId) {
        if(sessionId == null) return false;

        return CreatingBestPartyAuthentication.loggedinUserMap.containsKey(sessionId);
    }

    /**
     * try login
     * @param user_id
     * @param user_pwd
     * @param sessionId
     * @param mapper
     * @return
     */
    public static boolean login(String user_id, String user_pwd, String sessionId, UserMapper mapper) {
        if(user_id == null || user_pwd == null || sessionId == null) return false;
        if(isAuthenticated(sessionId)) return true;

        String encodedPassword = mapper.getUserCryptedPwd(user_id);

        boolean result = ENCODER.matches(user_pwd, encodedPassword);

        CreatingBestPartyAuthentication.loggedinUserMap.put(sessionId, user_id);

        return result;
    }

    /**
     * try logout
     * @param sessionId
     * @return
     */
    public static boolean logout(String sessionId) {
        if(sessionId == null) return false;
        if(!isAuthenticated(sessionId)) return false;

        CreatingBestPartyAuthentication.loggedinUserMap.remove(sessionId);

        return true;
    }
}
