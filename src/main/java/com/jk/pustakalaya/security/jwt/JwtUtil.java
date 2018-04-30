package com.jk.pustakalaya.security.jwt;

import java.util.Calendar;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtUtil {
	private static final JwtUtilHelper helper = new JwtUtilHelper();
	private static final long ZONE_OFFSET_VALUE = Calendar.getInstance().get(Calendar.ZONE_OFFSET);

	private static class JwtUtilHelper {
		private final JWTVerifier jwtVerifier;
		private final Builder jwtBuilder;
		private final Algorithm algorithmRs;

		// private RSAPublicKey publicKey = null;
		// private RSAPrivateKey privateKey = null;

		private JwtUtilHelper() {
			Algorithm alg = null;
			//this.algorithmRs = Algorithm.RSA256(publicKey, privateKey);

			try {
				alg = Algorithm.HMAC256("=s*7gpm_-WfmkUtEC6YWW&f9E3Bacd*$");
			}catch (Exception e) {
				e.printStackTrace();
			}

			this.algorithmRs = alg;
			jwtBuilder = JWT.create();
			jwtVerifier = JWT.require(algorithmRs).build();
		}


		private String encode (JwtPayload payload) throws JWTCreationException {
			Date issuedAt = new Date(System.currentTimeMillis() - ZONE_OFFSET_VALUE);
			Date expiresAt = new Date(issuedAt.getTime() + 15 * 60 * 1000);

			Builder builder =
					jwtBuilder
						.withClaim(JwtPayload.PAYLOAD_KEY_ID, payload.getId())
						.withClaim(JwtPayload.PAYLOAD_KEY_NAME, payload.getName())
						.withClaim(JwtPayload.PAYLOAD_KEY_EMAIL, payload.getEmail())
						.withArrayClaim(JwtPayload.PAYLOAD_KEY_ROLES, payload.getRoles())
						.withIssuedAt(issuedAt)
						.withExpiresAt(expiresAt);

			String token = builder.sign(algorithmRs);

			return token;
		}

		private JwtPayload decode(String token) throws JWTVerificationException {
			DecodedJWT jwt = jwtVerifier.verify(token);
			long now = System.currentTimeMillis() - ZONE_OFFSET_VALUE;

			if (jwt.getExpiresAt().getTime() < now) return null;

			JwtPayload payload =
					new JwtPayload(
							jwt.getClaim(JwtPayload.PAYLOAD_KEY_ID).asString(),
							jwt.getClaim(JwtPayload.PAYLOAD_KEY_NAME).asString(),
							jwt.getClaim(JwtPayload.PAYLOAD_KEY_EMAIL).asString(),
							jwt.getClaim(JwtPayload.PAYLOAD_KEY_ROLES).asArray(String.class)
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
