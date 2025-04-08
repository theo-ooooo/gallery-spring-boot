package net.kwkang.gallery.common.utill;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private static final Key signKey;
    
    static {
        String secretKey = "SECURITSY_KEY_202020231321321_!!";
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        signKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public static String generate(String subject, String name, Object value, int expMinutes) {
        Date expTime = new Date();

        expTime.setTime(expTime.getTime() + 1000L * 60 * expMinutes);

        // 기본정보 입력
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        // 클레임 입력
        Map<String, Object> claims = new HashMap<>();
        claims.put(name, value);

        // 토근 발급
        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setSubject(subject)
                .setExpiration(expTime)
                .addClaims(claims)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    public static boolean isValid(String token) {
        if(StringUtils.hasLength(token)) {
            try {
                Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {

            } catch (JwtException e) {
            }

        }
        return false;
    }

    public static Map<String, Object> getBody(String token) {
        return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
    }
}
