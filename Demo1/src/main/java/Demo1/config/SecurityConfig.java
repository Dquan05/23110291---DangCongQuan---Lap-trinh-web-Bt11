package Demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // ✅ Đặt ở đây thay vì trong Controller
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		// Người dùng admin
		UserDetails admin = User.withUsername("trung").password(encoder.encode("123")).roles("ADMIN") // ROLE_ADMIN
				.build();

		// Người dùng thường
		UserDetails user = User.withUsername("user").password(encoder.encode("123")).roles("USER") // ROLE_USER
				.build();

		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/hello").permitAll()
						.requestMatchers("/customer/**").authenticated().anyRequest().permitAll())
				.formLogin(form -> form.defaultSuccessUrl("/hello", true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/hello").permitAll());

		return http.build();
	}
}
