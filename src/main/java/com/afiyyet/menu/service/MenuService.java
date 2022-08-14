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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class MenuService {

	final private MenuMapper mapper;
	final private CategoryRepository categoryRepository;
	final private BrandRepository brandRepository;
	final private ProductRepository productRepository;

	public String KAMPANYALAR = "KAMPANYALAR";
	public String MENULER = "MENÜLER";

	public ServiceResult<MenuResponse> getMenu(String brandUniqueName) {
		try {
			Brand brand = brandRepository.findByUniqueName(brandUniqueName);
			List<Category> categories = categoryRepository.findAllByBrandId(brand.getId());
			categories = categories.stream()
					.filter(category -> !KAMPANYALAR.equals(category.getName()) && !MENULER.equals(category.getName()))
					.sorted(Comparator.comparing(Category::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());
			for (Category category : categories) {
				category.getProducts().sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
			}
			List<Product> campaigns = categoryRepository.findAllByBrandIdAndName(brand.getId(), KAMPANYALAR).getProducts();
			campaigns.sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
			campaigns = campaigns.stream()
					.filter(c -> ZonedDateTime.now().isAfter(c.getStartDate()) && ZonedDateTime.now().isBefore(c.getExpireDate()))
					.collect(Collectors.toList());
			List<Product> menus = categoryRepository.findAllByBrandIdAndName(brand.getId(), MENULER).getProducts();
			menus.sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
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

			if (dto.getLike()) {
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
