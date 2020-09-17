package com.smartmenu.product.service;

import com.smartmenu.client.product.ProductRequest;
import com.smartmenu.client.product.ProductResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.product.db.entity.Product;
import com.smartmenu.product.db.repository.ProductRepository;
import com.smartmenu.product.mapper.ProductMapper;
import com.smartmenu.product.mapper.ProductUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

	public ServiceResult<List<Product>> getProductsByCategory(String token, Long categoryId) {
		ServiceResult<List<Product>> serviceResult = new ServiceResult<>();
		try {
			Optional<List<Product>> entityList = Optional.of(getRepository().findAllByCategoryId(categoryId));
			serviceResult.setValue(entityList.get());
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
