package com.example.habitstracker.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

public class TokenAuthenticationService {
    private static long TOKEN_EXPIRY;
    //    private static final long TOKEN_EXPIRY = 600000000000L;
    private static String SECRET;
    //    private static final String SECRET = "veryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecretveryverybigsecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String ID_KEY = "userId";
    private static TokenAuthenticationService instance;

    public static void setSECRET(String SECRET) {
        TokenAuthenticationService.SECRET = SECRET;
    }

    public static void setTokenExpiry(long tokenExpiry) {
        TOKEN_EXPIRY = tokenExpiry;
    }

    public static TokenAuthenticationService getInstance() {
        if (instance != null)
            return instance;
        instance = new TokenAuthenticationService();
        return instance;
    }

    public void addAuthentication(HttpServletResponse response, String username, Long id) {
        var jwt = Jwts.builder()
                .setSubject(username)
                .claim(ID_KEY, id)
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        response.addHeader(AUTHORIZATION_HEADER_KEY, TOKEN_PREFIX + " " + jwt);
    }

    // Синтаксический разбор токена
    public Authentication getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (token != null) {
            var user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();

            if (user != null)
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<GrantedAuthority>());
        }
        return null;
    }

    public long getUserIdFromRequest(HttpServletRequest request) {
        var token = request.getHeader(AUTHORIZATION_HEADER_KEY).replace(TOKEN_PREFIX, "");
        var id = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().get(ID_KEY);
        return Long.parseLong(id.toString());
    }
}
