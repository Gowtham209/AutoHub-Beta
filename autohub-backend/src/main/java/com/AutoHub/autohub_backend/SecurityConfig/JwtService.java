package com.AutoHub.autohub_backend.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
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

	JwtService()
	{
		try {

			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		}
		catch (NoSuchAlgorithmException e)
		{
			throw  new RuntimeException(e);
		}
	}

	
	public String generateJwtToken(String emailId, Long userId, Integer role) {
		Map<String,Object> claims = new HashMap<>();
		claims.put("userId",userId);
		claims.put("role", role);
		return  Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}
	
	private Key getKey() {
		byte[] keyBytes= Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	

	public void extractBegin(String token) {
		// extract the username from jwt token
		extractClaim(token, Claims::getSubject);
		
	}


	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }


    // IMPORTANT for VALIDATION JWT 
	public boolean validateToken(String token, UserPrincipleDetailsImpl userDetails) {
		System.out.println("ValidateToken:\nuserName:"+userDetails.getUsername()+"\nuserId:"+userDetails.getUserId());
		final Long userId = extractUserId(token);
		System.out.println("Token:\nuserId:"+userId);
		return (userId==userDetails.getUserId() && !isTokenExpired(token));
	}
    
	public String extractUserName(String token) {
		// extract the username from jwt token
		return extractClaim(token, claims -> claims.get("emailId", String.class));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public Long extractUserId(String token) {
	    return extractClaim(token, claims -> claims.get("userId", Long.class));
	}

	public Byte extractUserRole(String token) {
	    return extractClaim(token, claims -> claims.get("role", Byte.class));
	}
	
//	public String generateJwtToken(String emailId) {
//
//		Map<String,Object> claims = new HashMap<>();
//		return  Jwts.builder()
//				.setClaims(claims)
//				.setSubject(emailId)
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
//				.signWith(getKey(), SignatureAlgorithm.HS256).compact();
//	}
//
//	public String extractUserName(String token) {
//		// extract the username from jwt token
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimResolver.apply(claims);
//	}
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getKey())
//                .build().parseClaimsJws(token).getBody();
//    }
//
//
//	public boolean validateToken(String token, UserDetails userDetails) {
//		final String userName = extractUserName(token);
//		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//
//	private boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
//
//	private Date extractExpiration(String token) {
//		return extractClaim(token, Claims::getExpiration);
//	}
}
