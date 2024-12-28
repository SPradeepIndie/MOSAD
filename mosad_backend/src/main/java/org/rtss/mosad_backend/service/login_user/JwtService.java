package org.rtss.mosad_backend.service.login_user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private String secretKey;

    //Generate Secret key when OBJECT is created
    //For each object generating key is different
    public JwtService() {
        KeyGenerator keyGene = null;
        try {
            keyGene = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGene.generateKey();
            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    /*-----------------------------
     * Services for generate token
     * -----------------------------*/
    public String generateToken(String username) {
        Map<String, Object> claims=new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .and()
                .signWith(getKey()) //sign the token with generated key
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyInBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyInBytes);
    }

    /*-----------------------------
    * Services for validate token
    * -----------------------------*/
    public String extractUsernameFromToken(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims=extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        return extractUsernameFromToken(jwtToken).equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,Claims::getExpiration);
    }
}
