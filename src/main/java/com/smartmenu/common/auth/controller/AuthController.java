package com.smartmenu.common.auth.controller;

import com.smartmenu.common.auth.request.LoginRequest;
import com.smartmenu.common.auth.request.RegisterRequest;
import com.smartmenu.common.auth.response.LoginResponse;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.common.user.db.repository.UserRepository;
import com.smartmenu.common.user.mapper.UserMapper;
import com.smartmenu.common.user.service.UserService;
import com.smartmenu.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-24
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

	final private AuthenticationManager authenticationManager;
	private BCryptPasswordEncoder passwordEncoder;
	final private UserRepository userRepository;
	final private UserService userService;
	final private UserMapper userMapper;
	final private JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(),
							request.getPassword()
					)
			);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password!", e);
		}

		final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
		ServiceResult<User> serviceResult = userService.getUserByUsername(request.getEmail());

		String token = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(token, userMapper.toResponse(serviceResult.getValue())));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

		User user = new User();
		user.setUsername(request.getEmail());
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setGender(request.getGender());
		user.setPassword(getPasswordEncoder().encode(request.getPassword()));
		user.setRoles(Collections.singletonList("USER"));

		try {
			User userSave = userRepository.save(user);
			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								request.getEmail(),
								request.getPassword()
						)
				);
			} catch (BadCredentialsException e) {
				throw new Exception("Incorrect username or password!", e);
			}

			final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());

			String token = jwtUtil.generateToken(userDetails);

			return ResponseEntity.ok(new LoginResponse(token, userMapper.toResponse(userSave)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().build();
	}

	private PasswordEncoder getPasswordEncoder() {
		if (passwordEncoder == null) {
			passwordEncoder = new BCryptPasswordEncoder();
		}
		return passwordEncoder;
	}
}
