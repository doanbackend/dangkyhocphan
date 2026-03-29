package space.emdon.dangkyhocphan.configuration;

import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import space.emdon.dangkyhocphan.validator.TokenValidator;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CustomJwtDecoder implements JwtDecoder {

final TokenValidator tokenValidator;

@Value("${spring.jwt.SIGNER_KEY}")
private String signerKey;

private NimbusJwtDecoder nimbusJwtDecoder = null;

@Override
public Jwt decode(String token) throws JwtException {
	try {
	tokenValidator.verifyToken(token, false);
	} catch (JOSEException | ParseException e) {
	throw new JwtException(e.getMessage());
	} catch (Exception e) {
	throw new JwtException("Token validation failed: " + e.getMessage());
	}

	if (Objects.isNull(nimbusJwtDecoder)) {
	SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HmacSHA256");

	nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
	}

	return nimbusJwtDecoder.decode(token);
}
}
