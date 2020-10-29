package com.smartmenu.brand.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.brand.mapper.BrandUpdateMapper;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.common.user.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class BrandService extends AbstractBaseService<BrandRequest, Brand, BrandResponse, BrandMapper> {
	final private BrandRepository repository;
	final private BrandMapper mapper;
	final private BrandUpdateMapper updateMapper;
	final private CategoryRepository categoryRepository;

	@Override
	public BrandRepository getRepository() {
		return repository;
	}

	@Override
	public BrandMapper getMapper() {
		return mapper;
	}

	@Override
	public BrandUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	@Override
	public ServiceResult<Brand> save(String token, BrandRequest request) {
		try {
			User user = getUser(token);
			if (!user.getRoles().contains(UserRoles.ADMIN.name())) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			Brand entity = getRepository().save(mapper.toEntity(request));
			categoryRepository.save(new Category("MENÜLER", -1, entity.getId()));
			categoryRepository.save(new Category("KAMPANYALAR", -2, entity.getId()));
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

}
