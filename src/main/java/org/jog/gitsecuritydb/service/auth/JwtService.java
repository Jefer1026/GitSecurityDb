package org.jog.gitsecuritydb.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jog.gitsecuritydb.persistence.entity.security.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;


    private SecretKey generateKey() {
        byte[] passwordDecoder = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(passwordDecoder);
    }

    private Claims extraClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(generateKey()).build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public String extractSubject(String jwt) {
        return extraClaims(jwt).getSubject();
    }

    public String generateToken(User user, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_IN_MINUTES * 30 * 1000) + issuedAt.getTime());

        return Jwts.builder()
                .header()
                .type("TYP")
                .and()

                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }
}
