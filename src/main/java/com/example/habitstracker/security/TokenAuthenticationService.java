package com.example.habitstracker.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.habitstracker.Constants;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Сервис для работы с JWT
 */
@Component
public class TokenAuthenticationService {
    private final long tokenExpiry;
    private final String secret;
    private final String TOKEN_PREFIX = "Bearer";
    private final String AUTHORIZATION_HEADER = "Authorization";
    private JwtParser jwtParser;

    @Autowired
    public TokenAuthenticationService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expire}") long expire) {
        this.secret = secret;
        buildJwtParser();
        tokenExpiry = expire;
    }

    private void buildJwtParser() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        // todo: убери дублирование 44-45 и 52-53
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public void addAuthentication(HttpServletResponse response, String username, Long id) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        var jwt = Jwts.builder()
                .setSubject(username)
                .claim(Constants.JWTClaims.USER_ID, id)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiry))
                .signWith(key)
                .compact();
        response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + jwt);
    }

    // Синтаксический разбор токена
    public Authentication getAuthentication(HttpServletRequest request) {
        var token = getAuthorizationToken(request);
        if (token == null)
            return null;
        token = getJWT(token);
        // todo: что делать если тут пустой токен?
        var username = jwtParser.parseClaimsJws(token).getBody().getSubject();
        if (username == null)
            return null;
        return new UsernamePasswordAuthenticationToken(username, extractUserCredentials(token), List.of());
    }

    private String getAuthorizationToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    private String getJWT(String token) {
        return token.replace(TOKEN_PREFIX, "");
    }

    private UserCredentials extractUserCredentials(String token) {
        var body = jwtParser.parseClaimsJws(token).getBody();
        var username = body.getSubject();
        var id = Long.parseLong(body.get(Constants.JWTClaims.USER_ID).toString());
        return new UserCredentials(id, username);
    }
}
