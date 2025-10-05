package Demo3.controller;

import Demo3.entity.Role;
import Demo3.entity.Users;
import Demo3.model.LoginDto;
import Demo3.model.SignUpDto;
import Demo3.repository.RoleRepository;
import Demo3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("❌ Username is already taken!", HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("❌ Email is already taken!", HttpStatus.BAD_REQUEST);
		}

		Users user = new Users();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		Role role = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role USER not found!"));
		user.setRoles(Collections.singleton(role));

		userRepository.save(user);
		return new ResponseEntity<>("✅ User registered successfully", HttpStatus.OK);
	}
}
