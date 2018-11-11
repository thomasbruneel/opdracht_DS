package applicationServer;

import java.util.Calendar;
import java.util.Date;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;



import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerator {
	public static final String masterSecretKey = "azerererfekddkffdkfkd";
    public static final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(masterSecretKey);
    public static final Key signingKey = new SecretKeySpec(apiKeySecretBytes,SignatureAlgorithm.HS256.getJcaName());
	
	public static String generate(String username){
		String token;
		
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 24); //token is 24 uur geldig
		
		token=Jwts.builder()
				.setId(username)
				.setIssuedAt(date)
				.setExpiration(calendar.getTime())
				.signWith(SignatureAlgorithm.HS256, signingKey)
				.compact();
		
		return token;
		
		
	}
	
	public static boolean CheckExpiration(String token){
		Jws<Claims> claims = Jwts.parser()
				  .setSigningKey(signingKey)
				  .parseClaimsJws(token);
		
		Date expirationDate=claims.getBody().getExpiration();
		Date date=new Date();
		
		System.out.println("date now: "+date.toString());
		System.out.println("expiration date: "+expirationDate.toString());
		
		if(date.compareTo(expirationDate)>0){

			System.out.println("Date is after expirationdate");
			return false;
			
		}
		
		else{

			System.out.println("Date is before expirationdate");
			return true;
			
		}
		
		
		
		
	}

}
