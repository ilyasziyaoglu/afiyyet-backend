package com.smartmenu.product.service;

import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.client.product.ArrangeProductRequest;
import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import com.smartmenu.product.db.entity.Product;
import com.smartmenu.product.db.repository.ProductRepository;
import com.smartmenu.product.mapper.ProductMapper;
import com.smartmenu.product.mapper.ProductUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class ProductService extends AbstractBaseService<ProductRequest, Product, ProductResponse, ProductMapper> {
	final private ProductRepository repository;
	final private ProductMapper mapper;
	final private ProductUpdateMapper updateMapper;
	final private CategoryRepository categoryRepository;

	@Override
	public ProductRepository getRepository() {
		return repository;
	}

	@Override
	public ProductMapper getMapper() {
		return mapper;
	}

	@Override
	public ProductUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	@Override
	public ServiceResult<Product> save(String token, ProductRequest request) {
		ServiceResult<Product> serviceResult = new ServiceResult<>();
		try {
			Category category =  categoryRepository.getOne(request.getCategory().getId());
			Product entityToSave = getMapper().toEntity(request);
			entityToSave.setCategory(category);
			Product entity = getRepository().save(entityToSave);
			serviceResult.setValue(entity);
			serviceResult.setHttpStatus(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setMessage("Entity can not save. Error message: " + e.getMessage());
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return serviceResult;
	}

	public ServiceResult<List<Product>> getProductsByCategory(String token, Long categoryId) {
		ServiceResult<List<Product>> serviceResult = new ServiceResult<>();
		try {
			List<Product> entityList = getRepository().findAllByCategoryId(categoryId);
			entityList = entityList.stream().sorted(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList());
			serviceResult.setValue(entityList);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}

	public ServiceResult<Boolean> arrangeProducts(String token, List<ArrangeProductRequest> dto) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<>();

		User user = getUser(token);

		Set<Long> ids = dto.stream().map(ArrangeProductRequest::getId).collect(Collectors.toSet());
		try {
			Optional<List<Product>> entityList = Optional.of(getRepository().findAllById(ids));
			int index = 0;
			for (Product category : entityList.get()) {
				if (user.getBrand().equals(category.getCategory().getBrand())) {
					category.setOrder(dto.get(index++).getOrder());
					getRepository().save(category);
				}
			}
			serviceResult.setValue(true);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
