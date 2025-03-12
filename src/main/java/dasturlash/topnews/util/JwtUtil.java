package dasturlash.topnews.util;

import dasturlash.topnews.dto.JwtDTO;
import dasturlash.topnews.entity.ProfileRoleEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    @Value("${jwt.secret.key}")
    private static String secretKey;
    @Value("${jwt.token.live.time}")
    private static int tokenLiveTime;

    public static String encode(String username, Integer id, ProfileRoleEntity roles){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("id", String.valueOf(id));
        claims.put("roles", roles.getRole().name());

        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        String username = claims.get("username").toString();
        String role = (String) claims.get("roles");
        return new JwtDTO(username, role);

    }

    private static SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
