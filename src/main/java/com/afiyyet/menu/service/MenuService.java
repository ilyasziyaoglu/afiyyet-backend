package com.afiyyet.menu.service;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.brand.db.repository.BrandRepository;
import com.afiyyet.category.db.entity.Category;
import com.afiyyet.category.db.repository.CategoryRepository;
import com.afiyyet.client.menu.LikeRequest;
import com.afiyyet.client.menu.MenuResponse;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.menu.mapper.MenuMapper;
import com.afiyyet.menu.model.Menu;
import com.afiyyet.product.db.entity.Product;
import com.afiyyet.product.db.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.afiyyet.common.enums.Status.DEACTIVE;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuMapper mapper;
	private final CategoryRepository categoryRepository;
	private final BrandRepository brandRepository;
	private final ProductRepository productRepository;

	public String KAMPANYALAR = "KAMPANYALAR";
	public String MENULER = "MENÜLER";

	public ServiceResult<MenuResponse> getMenu(String brandUniqueName) {
		try {
			Brand brand = brandRepository.findByUniqueName(brandUniqueName);
			List<Category> categories = categoryRepository.findAllByBrandId(brand.getId());
			categories = categories.stream()
					.filter(category -> !KAMPANYALAR.equals(category.getName()) && !MENULER.equals(category.getName()))
					.filter(category -> category.getStatus() != DEACTIVE)
					.sorted(Comparator.comparing(Category::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());
			for (Category category : categories) {
				category.setProducts(category.getProducts().stream().filter(product -> product.getStatus() != DEACTIVE).collect(Collectors.toList()));
				category.getProducts().sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
			}
			List<Product> campaigns = categoryRepository.findAllByBrandIdAndName(
					brand.getId(),
					KAMPANYALAR
				)
				.getProducts()
				.stream()
				.filter(product -> product.getStatus() != DEACTIVE)
				.sorted(Comparator.comparing(
					Product::getOrder,
					Comparator.nullsLast(Comparator.naturalOrder())
				))
				.collect(Collectors.toList());
			campaigns = campaigns.stream()
					.filter(c -> ZonedDateTime.now().isAfter(c.getStartDate()) && ZonedDateTime.now().isBefore(c.getExpireDate()))
					.collect(Collectors.toList());
			List<Product> menus = categoryRepository.findAllByBrandIdAndName(brand.getId(), MENULER)
				.getProducts()
				.stream()
				.filter(product -> product.getStatus() != DEACTIVE)
				.sorted(Comparator.comparing(
					Product::getOrder,
					Comparator.nullsLast(Comparator.naturalOrder())
				))
				.collect(Collectors.toList());
			return new ServiceResult<>(mapper.toResponse(new Menu(categories, campaigns, menus, brand)));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> like(LikeRequest dto) {
		try {
			Product product = productRepository.getOne(dto.getItemId());
			Brand brand = brandRepository.getOne(product.getCategory().getBrandId());
			if (!brand.getFeatures().contains(FeatureType.LIKE)) {
				return forbiddenBoolean();
			}

			if (BooleanUtils.isTrue(dto.getLike())) {
				product.setLikes(product.getLikes() + 1);
			} else if (product.getLikes() > 0) {
				product.setLikes(product.getLikes() - 1);
			}
			productRepository.save(product);
			return new ServiceResult<>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<Boolean> forbiddenBoolean() {
		return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
	}
}
