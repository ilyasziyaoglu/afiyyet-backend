package com.afiyyet.common.auth.controller;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.brand.db.repository.BrandRepository;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.category.db.repository.CategoryRepository;
import com.afiyyet.client.user.Gender;
import com.afiyyet.common.auth.request.LoginRequest;
import com.afiyyet.common.auth.request.RegisterRequest;
import com.afiyyet.common.auth.response.LoginResponse;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.enums.Status;
import com.afiyyet.common.user.db.entity.User;
import com.afiyyet.common.user.db.repository.UserRepository;
import com.afiyyet.common.user.mapper.UserMapper;
import com.afiyyet.common.user.service.UserService;
import com.afiyyet.common.utils.JwtUtil;
import com.afiyyet.rtable.db.entity.RTable;
import com.afiyyet.rtable.db.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

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
			insertAdminUser();
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

		return ResponseEntity.ok(new ServiceResult<>(new LoginResponse(token, userMapper.toResponse(serviceResult.getValue()))));
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

			return ResponseEntity.ok(new ServiceResult<>(new LoginResponse(token, userMapper.toResponse(userSave))));
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

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	private void insertAdminUser() {
		Optional<User> userOptional = userRepository.findByUsername("ilyas");

		if (!userOptional.isPresent()) {
			Brand brand = new Brand();
			brand.setName("Pusula Tech");
			brand.setStatus(Status.ACTIVE);
			brand.setUniqueName("pusulatech");
			brand.setFeatures(new HashSet<>(FeatureType.getVip()));
			brand.setLogoImgUrl("https://store-images.s-microsoft.com/image/apps.903.9007199266516668.daba864c-ff34-4261-8cf2-3b0ff1ed7211.ebc446c9-4184-4451-8c6e-398e7c8aa0ff?mode=scale&q=90&h=300&w=300");
			brand = brandRepository.save(brand);

			Category campaigns = new Category();
			campaigns.setBrandId(brand.getId());
			campaigns.setName("KAMPANYALAR");
			campaigns.setStatus(Status.ACTIVE);
			categoryRepository.save(campaigns);

			Category menus = new Category();
			menus.setBrandId(brand.getId());
			menus.setName("MENÜLER");
			menus.setStatus(Status.ACTIVE);
			categoryRepository.save(menus);

			RTable table = new RTable();
			table.setBrand(brand);
			table.setName("Table 1");
			table.setOpen(false);
			table.setGroupName("Salon");
			tableRepository.save(table);

			User user = new User();
			user.setEmail("ilyasziyaoglu@gmail.com");
			user.setGender(Gender.MALE);
			user.setFullName("İlyas Ziyaoğlu");
			user.setUsername("ilyas");
			user.setStatus(Status.ACTIVE);
			user.setBrand(brand);
			user.setCountry("TR");
			user.setImageUrl("https://store-images.s-microsoft.com/image/apps.903.9007199266516668.daba864c-ff34-4261-8cf2-3b0ff1ed7211.ebc446c9-4184-4451-8c6e-398e7c8aa0ff?mode=scale&q=90&h=300&w=300");
			user.setPassword(new BCryptPasswordEncoder().encode("12345"));
			user.setPhone("05061107443");
			user.setRoles(Arrays.asList("ADMIN", "MANAGER", "USER"));
			userRepository.save(user);
		}
	}
}
