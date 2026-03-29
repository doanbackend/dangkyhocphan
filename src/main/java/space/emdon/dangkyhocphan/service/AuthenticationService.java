package space.emdon.dangkyhocphan.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.*;
import java.text.ParseException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import space.emdon.dangkyhocphan.dto.request.*;
import space.emdon.dangkyhocphan.dto.response.*;
import space.emdon.dangkyhocphan.exception.*;
import space.emdon.dangkyhocphan.rbac.role.Role;
import space.emdon.dangkyhocphan.rbac.user.User;
import space.emdon.dangkyhocphan.rbac.user.UserRepository;
import space.emdon.dangkyhocphan.validator.TokenValidator;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

final UserRepository userRepository;
final TokenValidator tokenValidator;
final PasswordEncoder passwordEncoder;

@NonFinal
@Value("${spring.jwt.SIGNER_KEY}")
protected String SIGNER_KEY;

@NonFinal
@Value("${spring.jwt.valid_duration}")
protected String VALID_DURATION;

@NonFinal
@Value("${spring.jwt.refreshable_duration}")
protected String REFRESHABLE_DURATION;

public IntrospectResponse introspect(IntrospectRequest request)
	throws JOSEException, ParseException {
	try {
	tokenValidator.verifyToken(request.getToken(), false);
	} catch (AppException e) {
	return IntrospectResponse.builder().valid(false).build();
	}
	return IntrospectResponse.builder().valid(true).build();
}

public AuthenticationResponse authenticate(AuthenticationRequest request) {
	User user =
		userRepository
			.findByEmail(request.getEmail())
			.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
	boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

	if (!authenticated) throw new AppException(ErrorCode.INVALID_CREDENTIAL);

	var token = generateToken(user);

	return AuthenticationResponse.builder().token(token).authenticated(true).build();
}

public void logout(LogoutRequest request) throws ParseException, JOSEException {
	SignedJWT signedJwt = tokenValidator.verifyToken(request.getToken(), false);
	String jti = signedJwt.getJWTClaimsSet().getJWTID();
	Date expiryTime = signedJwt.getJWTClaimsSet().getExpirationTime();

	tokenValidator.blacklistToken(jti, expiryTime);
}

public AuthenticationResponse refreshToken(RefreshRequest request)
	throws ParseException, JOSEException {
	SignedJWT signedJwt = tokenValidator.verifyToken(request.getToken(), true);
	var jti = signedJwt.getJWTClaimsSet().getJWTID();
	var expiryTime = signedJwt.getJWTClaimsSet().getExpirationTime();
	tokenValidator.blacklistToken(jti, expiryTime);
	var email = signedJwt.getJWTClaimsSet().getSubject();
	var user =
		userRepository
			.findByEmail(email)
			.orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
	var token = generateToken(user);
	return AuthenticationResponse.builder().token(token).authenticated(true).build();
}

private String generateToken(User user) {
	JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

	JWTClaimsSet claimsSet =
		new JWTClaimsSet.Builder()
			.subject(user.getEmail())
			.issuer("emdon")
			.jwtID(UUID.randomUUID().toString())
			.issueTime(new Date())
			.expirationTime(
				new Date(
					Instant.now()
						.plus(Long.parseLong(VALID_DURATION), ChronoUnit.HOURS)
						.toEpochMilli()))
			.claim("scope", buildScope(user))
			.claim("roles", user.getRoles().stream().map(Role::getName).toList())
			.build();

	Payload payload = new Payload(claimsSet.toJSONObject());

	JWSObject jwsObject = new JWSObject(header, payload);

	try {
	jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
	} catch (JOSEException e) {
	throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
	}

	return jwsObject.serialize();
}

private String buildScope(User user) {
	StringJoiner stringJoiner = new StringJoiner(" ");
	if (!CollectionUtils.isEmpty(user.getRoles())) {
	user.getRoles()
		.forEach(
			role -> {
				stringJoiner.add("ROLE_" + role.getName());
				if (!CollectionUtils.isEmpty(role.getPermissions())) {
				role.getPermissions()
					.forEach(permission -> stringJoiner.add(permission.getName()));
				}
			});
	}
	return stringJoiner.toString();
}
}
