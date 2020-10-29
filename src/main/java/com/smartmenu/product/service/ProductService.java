package com.smartmenu.product.service;

import com.smartmenu.brand.db.model.FeatureType;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.client.product.BulkPriceUpdateRequest;
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

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

	public ServiceResult<Product> save(String token, ProductRequest request) {
		try {
			User user = getUser(token);
			Category category =  categoryRepository.getOne(request.getCategory().getId());
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS) || !user.getBrand().getId().equals(category.getBrandId())) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}

			Product entityToSave = getMapper().toEntity(request);
			entityToSave.setLikes(0);
			entityToSave.setCategory(category);
			Product entity = getRepository().save(entityToSave);
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	@Override
	public ServiceResult<Product> update(String token, @NotNull ProductRequest request) {
		try {
			User user = getUser(token);
			Category category = categoryRepository.getOne(request.getCategory().getId());
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS) || !user.getBrand().getId().equals(category.getBrandId())) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			Optional<Product> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				Product newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
				repository.save(newEntity);
				return new ServiceResult<>(newEntity, HttpStatus.OK);
			} else {
				return new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity not found to update update with the given id: " + request.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.NOT_MODIFIED, "Entity can not update with the given id: " + request.getId() + ". Error message: " + e.getMessage());
		}
	}

	public ServiceResult<List<Product>> getProductsByCategory(String token, Long categoryId) {
		try {
			List<Product> entityList = getRepository().findAllByCategoryId(categoryId);
			entityList = entityList.stream().sorted(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			return new ServiceResult<>(entityList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<Boolean> arrangeProducts(String token, Map<Long, Integer> dto) {
		try {
			User user = getUser(token);
			if (!getUser(token).getBrand().getFeatures().contains(FeatureType.ORDERING)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			Optional<List<Product>> entityList = Optional.of(getRepository().findAllById(dto.keySet()));
			for (Product prodduct : entityList.get()) {
				if (user.getBrand().getId().equals(prodduct.getCategory().getBrandId())) {
					prodduct.setOrder(dto.get(prodduct.getId()));
					getRepository().save(prodduct);
				}
			}
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<Boolean> bulkPriceUpdate(String token, BulkPriceUpdateRequest dto) {
		try {
			User user = getUser(token);
			categoryRepository.findAllByBrandId(user.getBrand().getId()).forEach(category -> {
				category.getProducts().forEach(product -> {
					if (dto.isPercent()) {
						product.setPrice(updatePriceByPercent(product.getPrice(), dto.getAmount()));
					} else {
						product.setPrice(product.getPrice().add(BigDecimal.valueOf(dto.getAmount())));
					}
					repository.save(product);
				});
			});
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	private BigDecimal updatePriceByPercent(BigDecimal price, Double percent) {
		BigDecimal newPrice = price.multiply(BigDecimal.valueOf(1 + percent/100));
		BigDecimal diff = newPrice.remainder(BigDecimal.ONE);
		if (diff.compareTo(BigDecimal.valueOf(0.25)) < 0) {
			newPrice = newPrice.setScale(0, RoundingMode.FLOOR);
		} else if (diff.compareTo(BigDecimal.valueOf(0.25)) >= 0 && diff.compareTo(BigDecimal.valueOf(0.75)) < 0) {
			newPrice = newPrice.setScale(0, RoundingMode.FLOOR).add(BigDecimal.valueOf(0.5));
		} else {
			newPrice = newPrice.setScale(0, RoundingMode.CEILING);
		}
		return newPrice;
	}
}
