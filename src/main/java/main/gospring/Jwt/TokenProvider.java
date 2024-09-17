package main.gospring.Jwt;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import main.gospring.model.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    // secret key
    private static final String SECRET_KEY = "asdasdmasdasbdamndbsamdnbqwdqdqwdiwrbqwfbjkdb12jk3SADCSADQWDWDQWDDWDASSADSXaASDASDAS";

//    // token 생성
//    public String generateToken(User user) {
//
//        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
//
//        return Jwts.builder()
//                // header의 alg: HS512
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                // payload
//                .setExpiration(expiration)
//                .setIssuer("go spring app")
//                .setIssuedAt(new Date())
//                .setSubject(user.getUsername())
//                .compact();
//    }

    // token 생성
    // User는 UserDetails를 구현하기 때문에 Authentication에서 정보를 가져 올 수 있음
    // 추가로 payload에 username이외에 id 추가
    public String generateToken(User user) {

        String id = user.getId();
        String username = user.getUsername();

        // token 수명
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        return Jwts.builder()
                // header의 alg: HS512
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload
                .setExpiration(expiration)
                .setIssuer("go spring app")
                .setIssuedAt(new Date())
                .setSubject(username)
                .claim("id", id)
                .compact();
    }

    // claim에서 id 추출
    public Long getUserIdFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    // claim에서 username 추출
    public String getUsernameFromToken(String token) {
        
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String validateTokenByUsername(String token) {

        // secretkey로 decode
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        // claims의 subject인 username
        return claims.getSubject();
    }
}
