package com.chat.security;

import com.chat.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public class JwtTokenUtil {

    private JwtTokenUtil(){}

    public static  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes())
                .parseClaimsJws(token).getBody();
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public static String createToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .claim("user_id", user.getId())
                .claim("user_full_name", user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                .compact();
    }

    public static String refreshToken(String token) {
        final Claims claims = extractAllClaims(token);

        Date now = new Date();
        claims.setIssuedAt(now);
        claims.setExpiration(new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME));

        return createTokenFromClaims(claims);
    }

    private static String createTokenFromClaims(Claims claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes()).compact();
    }
}
