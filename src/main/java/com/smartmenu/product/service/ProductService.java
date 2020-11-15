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
import com.smartmenu.product.enums.ProductType;
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

	public ServiceResult<ProductResponse> get(Long id) {
		try {
			return new ServiceResult<>(mapper.toResponse(repository.getOne(id)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<ProductResponse> insert(String token, ProductRequest request) {
		try {
			User user = getUser(token);
			Category category = getCategoryByType(request, user);
			if (category == null) {
				return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: category not found or product type mismatch!");
			}

			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS) || !user.getBrand().getId().equals(category.getBrandId())) {
				return forbidden();
			}

			Product entityToSave = getMapper().toEntity(request);
			entityToSave.setLikes(0);
			entityToSave.setCategory(category);
			Product entity = getRepository().save(entityToSave);
			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	private Category getCategoryByType(ProductRequest request, User user) {
		if (ProductType.PRODUCT.equals(request.getType())) {
			return categoryRepository.getOne(request.getCategory().getId());
		} else if (ProductType.CAMPAIGN.equals(request.getType())) {
			return categoryRepository.findAllByBrandIdAndName(user.getBrand().getId(), KAMPANYALAR);
		} else if (ProductType.MENU.equals(request.getType())) {
			return categoryRepository.findAllByBrandIdAndName(user.getBrand().getId(), MENULER);
		}
		return null;
	}

	public ServiceResult<ProductResponse> update(String token, @NotNull ProductRequest request) {
		try {
			User user = getUser(token);
			Category category = getCategoryByType(request, user);
			if (category == null) {
				return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: category not found or product type mismatch!");
			}
			if (!user.getBrand().getFeatures().contains(FeatureType.CRUD_OPERATIONS) || !user.getBrand().getId().equals(category.getBrandId())) {
				return forbidden();
			}
			Optional<Product> entity = getRepository().findById(request.getId());
			if (entity.isPresent()) {
				Product newEntity = getUpdateMapper().toEntityForUpdate(request, entity.get());
				return new ServiceResult<>(mapper.toResponse(repository.save(newEntity)));
			} else {
				return new ServiceResult<>(HttpStatus.NOT_FOUND, "Entity not found to update update with the given id: " + request.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> delete(String token, Long id) {
		try {
			User user = getUser(token);
			Product entity = repository.getOne(id);
			if (!isNotAdmin(user) && !entity.getCategory().getBrandId().equals(user.getBrand().getId())) {
				return forbiddenBoolean();
			}
			repository.delete(entity);
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<List<ProductResponse>> getProductsByCategory(String token, Long categoryId) {
		try {
			List<Product> entityList = getRepository().findAllByCategoryId(categoryId);
			entityList = entityList.stream().sorted(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			return new ServiceResult<>(mapper.toResponse(entityList));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> arrangeProducts(String token, Map<Long, Integer> dto) {
		try {
			User user = getUser(token);
			if (!getUser(token).getBrand().getFeatures().contains(FeatureType.ORDERING)) {
				return forbiddenBoolean();
			}
			Optional<List<Product>> entityList = Optional.of(getRepository().findAllById(dto.keySet()));
			for (Product prodduct : entityList.get()) {
				if (user.getBrand().getId().equals(prodduct.getCategory().getBrandId())) {
					prodduct.setOrder(dto.get(prodduct.getId()));
					getRepository().save(prodduct);
				}
			}
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
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
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
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
