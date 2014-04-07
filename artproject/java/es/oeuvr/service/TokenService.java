package es.oeuvr.service;

import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenService {
	
	final static int EXPIRED =  0;
	final static int INVALID = -1;
	final static int VALID   =  1;
	final static String KEY = "527756D3-4F83-4C52-A689-78A231E5A225";
	final static String KEY_ID = "123749238";

	public static String creatToken(String subject) throws ParseException, NoSuchAlgorithmException, MalformedURLException, JOSEException {
		JWTClaimsSet jwtClaims = new JWTClaimsSet();
		jwtClaims.setSubject(subject);
		//jwtClaims.setIssuer("https://c2id.com");
		jwtClaims.setExpirationTime(new Date(new Date().getTime() + 1000*60*60*8));
		//jwtClaims.setNotBeforeTime(new Date());
		//jwtClaims.setIssueTime(new Date());
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
		header.setKeyID(UUID.randomUUID().toString());
		//header.setJWKURL(new URL("https://c2id.com/jwks.json"));
		SignedJWT signedJWT = new SignedJWT(header, jwtClaims);
		
		JWSSigner signer = new MACSigner(KEY.getBytes());
		signedJWT.sign(signer);
		String serializedJWT = signedJWT.serialize();
		return serializedJWT;
	}
	
	
	public static String getSubjectFromToken(String serializedJWT) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(serializedJWT);
		return signedJWT.getJWTClaimsSet().getSubject();
	}
}
