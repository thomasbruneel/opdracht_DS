package applicationServer;

import java.util.Calendar;
import java.util.Date;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerator {
	
	public static String generate(String username){
		String token;
		
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 24); //token is 24 uur geldig
		
		token=Jwts.builder().setId(username).setIssuedAt(date).setExpiration(calendar.getTime()).signWith(SignatureAlgorithm.HS256, "key").compact();
		
		return token;
		
		
	}

}
