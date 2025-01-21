package org.rtss.mosad_backend.service.login_user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey;
    private static final long accessTokenExpirationTime=1000L*60;//1min
    private static final long refreshTokenExpirationTime=1000L*60*60*24*7;//7days

    //Generate Secret key, when OBJECT is created
    //For each object generating key is different
    public JwtService() {
        try {
            KeyGenerator keyGene = KeyGenerator.getInstance("HmacSHA256");
            SecretKey accessSK = keyGene.generateKey();
            secretKey = Base64.getEncoder().encodeToString(accessSK.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    /*-----------------------------
     * Services for generate token
     * -----------------------------*/
    public String generateToken(String username,String role) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("role",role);
        return buildToken(claims,username,accessTokenExpirationTime);
    }

    //In refresh has not claims. It will live long
    public String generateRefreshToken(String username) {
        return buildToken(new HashMap<>(),username,refreshTokenExpirationTime);
    }

    //Private method for building the token
    private String buildToken(
            Map<String, Object> claims,
            String username,
            long expirationTime
    ){
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expirationTime))
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
