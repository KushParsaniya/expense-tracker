package dev.kush.expensetracker.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    // TODO: retrive it from env variable
    private static String SECRET_KEY = "171770cbfbbba7d5140b71684ed4ed4a20e3d25802fe46d87fae95e4efd8468af2a747e12e859eedd20b4d1cf2cfa1d0f7ae0e46e5d7ae0274b706a9d369420531b0bc1dedd4734468cf8d79e83a3ad083d14fb6e167ed0d949fb24f8d37870ef24a242b70fbdba89499aa83056bc8418838b20f20429e6e86bf549bb9429968df36ca30be8ac894fdb8b2c3fc6349ae3fd2ec800a174c7dfab0c5ee1d52a1e3bbb8843f72be8299439497925ec4db41c5596807b19279676ccfd730dacb97ecb0d1f81421c8237c157741d68842f23012ce0382b09d8cce689bfb5a4f8a29b3bb2e49fc33dea6d113ac4b40f05f8c5f68f0f0c44a1672a6983720c510229e6b";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    /*
    TODO: here try to use username(email) instead of UserDetails so
           it we don't need to retrieve user from DB
     */
    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 3600 * 1000))
                .signWith(key)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return userDetails.getUsername().equals(username) && !isExpired(token);
    }
}