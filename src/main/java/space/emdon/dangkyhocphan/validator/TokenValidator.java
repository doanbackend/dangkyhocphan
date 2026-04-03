package space.emdon.dangkyhocphan.validator;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import space.emdon.dangkyhocphan.exception.AppException;
import space.emdon.dangkyhocphan.exception.ErrorCode;
import space.emdon.dangkyhocphan.repository.InvalidatedTokenRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class TokenValidator {

final InvalidatedTokenRepository invalidatedTokenRepository;

@NonFinal
@Value("${spring.jwt.SIGNER_KEY}")
protected String SIGNER_KEY;

@Value("${spring.jwt.refreshable_duration}")
protected String REFRESHABLE_DURATION;

/**
* Verify token signature and check if token is blacklisted
*
* @param token JWT token string
* @return SignedJWT if valid
* @throws JOSEException if signature verification fails
* @throws ParseException if token parsing fails
*/
public SignedJWT verifyToken(String token, boolean isRefreshToken)
	throws JOSEException, ParseException {
	JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
	SignedJWT signedJwt = SignedJWT.parse(token);

	Date expiryTime =
		(isRefreshToken)
			? new Date(
				signedJwt
					.getJWTClaimsSet()
					.getIssueTime()
					.toInstant()
					.plus(Long.parseLong(REFRESHABLE_DURATION), ChronoUnit.HOURS)
					.toEpochMilli())
			: signedJwt.getJWTClaimsSet().getExpirationTime();
	var verified = signedJwt.verify(verifier);

	if (!(verified && expiryTime.after(new Date()))) {
	throw new AppException(ErrorCode.INVALID_TOKEN);
	}

	String jti = signedJwt.getJWTClaimsSet().getJWTID();
	if (invalidatedTokenRepository.existsById(jti)) {
	throw new AppException(ErrorCode.UNAUTHENTICATED);
	}

	return signedJwt;
}

/**
* Check if token JTI is in blacklist
*
* @param jti JWT ID
* @return true if token is blacklisted
*/
public boolean isTokenBlacklisted(String jti) {
	return invalidatedTokenRepository.existsById(jti);
}

/**
* Add token to blacklist
*
* @param jti JWT ID
* @param expiryTime Token expiry time
*/
public void blacklistToken(String jti, Date expiryTime) {
	space.emdon.dangkyhocphan.entity.InvalidatedToken token =
		space.emdon.dangkyhocphan.entity.InvalidatedToken.builder()
			.id(jti)
			.expiryTime(expiryTime)
			.build();
	invalidatedTokenRepository.save(token);
}
}
