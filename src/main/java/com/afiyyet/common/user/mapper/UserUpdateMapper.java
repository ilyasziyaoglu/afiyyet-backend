package com.afiyyet.common.user.mapper;

import com.afiyyet.brand.mapper.BrandMapper;
import com.afiyyet.client.user.UserRequest;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import com.afiyyet.common.user.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class UserUpdateMapper implements BaseUpdateMapper<UserRequest, User> {

	final private BrandMapper brandMapper;

	@Override
	public User toEntityForUpdate(UserRequest request, User entity) {
		if (entity == null) {
			entity = new User();
		}

		if (request.getEmail() != null) {
			entity.setEmail(request.getEmail());
		}
		if (request.getUsername() != null) {
			entity.setUsername(request.getUsername());
		}
		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}
		if (request.getFullName() != null) {
			entity.setFullName(request.getFullName());
		}
		if (request.getImageUrl() != null) {
			entity.setImageUrl(request.getImageUrl());
		}
		if (request.getGender() != null) {
			entity.setGender(request.getGender());
		}
		if (request.getPhone() != null) {
			entity.setPhone(request.getPhone());
		}
		if (request.getBirthdate() != null) {
			entity.setBirthdate(request.getBirthdate());
		}
		if (request.getCountry() != null) {
			entity.setCountry(request.getCountry());
		}
		if (request.getAddresses() != null) {
			entity.setAddresses(request.getAddresses());
		}

		return entity;
	}
}
