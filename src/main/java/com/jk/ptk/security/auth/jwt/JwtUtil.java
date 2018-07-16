package com.jk.ptk.security.auth.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jk.ptk.app.App;


public class JwtUtil {
	public static final String PAYLOAD_KEY_NAME = "name";
	public static final String PAYLOAD_KEY_EMAIL = "email";
	public static final String PAYLOAD_KEY_ROLE = "role";
	public static final String PAYLOAD_KEY_LOCALE = "locale";

	private static Logger log = LoggerFactory.getLogger(JwtUtil.class);
	private static final JwtUtilHelper helper = new JwtUtilHelper();

	private static class JwtUtilHelper {
		private final JWTVerifier jwtVerifier;
		private final Builder jwtBuilder;
		private final Algorithm algorithmRs;
		private final long MINTUES_TOKEN_AGE = 600;

		// private RSAPublicKey publicKey = null;
		// private RSAPrivateKey privateKey = null;

		private JwtUtilHelper() {
			Algorithm alg = null;
			//this.algorithmRs = Algorithm.RSA256(publicKey, privateKey);

			try {
				String hmacSecret = App.getHmacSecret();
				alg = Algorithm.HMAC256(hmacSecret);
			}catch (Exception e) {
				log.error("Exception while getting alogrithm {}", e);
			}

			this.algorithmRs = alg;
			jwtBuilder = JWT.create();
			jwtVerifier = JWT.require(algorithmRs).build();
		}


		private String encode (JwtPayload payload) throws JWTCreationException {
			Date issuedAt = new Date(System.currentTimeMillis());
			Date expiresAt = new Date(issuedAt.getTime() + MINTUES_TOKEN_AGE * 60 * 1000);

			Builder builder =
					jwtBuilder
						.withClaim(PAYLOAD_KEY_NAME, payload.getName())
						.withClaim(PAYLOAD_KEY_EMAIL, payload.getEmail())
						.withClaim(PAYLOAD_KEY_ROLE, payload.getRole())
						.withClaim(PAYLOAD_KEY_LOCALE, payload.getLocale())
						.withIssuedAt(issuedAt)
						.withExpiresAt(expiresAt);

			String token = builder.sign(algorithmRs);

			return token;
		}

		private JwtPayload decode(String token) throws JWTVerificationException {
			DecodedJWT jwt = jwtVerifier.verify(token);

			JwtPayload payload =
					new JwtPayload(
							jwt.getClaim(PAYLOAD_KEY_NAME).asString(),
							jwt.getClaim(PAYLOAD_KEY_EMAIL).asString(),
							jwt.getClaim(PAYLOAD_KEY_ROLE).asString(),
							jwt.getClaim(PAYLOAD_KEY_LOCALE).asString()
							);

			return payload;
		}


		private boolean isAuthenticated(String token) throws JWTVerificationException {
			return this.decode(token) != null;
		}

	}


	private JwtUtil() {}

	public static JwtPayload decode(String jwt) throws JWTCreationException {
		return helper.decode(jwt);
	}

	public static String encode(JwtPayload payload) throws JWTVerificationException {
		return helper.encode(payload);
	}

	public static boolean isAuthenticated(String token) throws JWTVerificationException {
		return helper.isAuthenticated(token);
	}
}
