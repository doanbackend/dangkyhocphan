package space.emdon.dangkyhocphan.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
private final String[] PUBLIC_ENDPOINTS = {"/users", "/auth/**"};

@Value("${spring.jwt.SIGNER_KEY}")
protected String SIGNER_KEY;

@Autowired private CustomJwtDecoder customJwtDecoder;

@Bean
public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

	httpSecurity.authorizeHttpRequests(
		requests ->
			requests
				.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
				.permitAll()
				.anyRequest()
				.authenticated());

	httpSecurity.oauth2ResourceServer(
		oauth2 ->
			oauth2
				.jwt(
					jwtConfigurer ->
						jwtConfigurer
							.decoder(customJwtDecoder)
							.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

	httpSecurity.csrf(csrf -> csrf.disable());

	return httpSecurity.build();
}

@Bean
PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder(10);
}
@Bean
public CorsFilter corsFilter() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);

    return new CorsFilter(urlBasedCorsConfigurationSource);
}

@Bean
public JwtAuthenticationConverter jwtAuthenticationConverter() {

	JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
	authoritiesConverter.setAuthorityPrefix("");

	JwtAuthenticationConverter jwtAuthConverter = new JwtAuthenticationConverter();
	jwtAuthConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

	return jwtAuthConverter;
}
}
