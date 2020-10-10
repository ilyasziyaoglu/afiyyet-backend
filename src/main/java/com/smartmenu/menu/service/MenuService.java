package com.smartmenu.menu.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.model.FeatureType;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.db.repository.CampaignRepository;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.client.menu.LikeRequest;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.menu.model.Menu;
import com.smartmenu.product.db.entity.Product;
import com.smartmenu.product.db.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

	final private CategoryRepository categoryRepository;
	final private BrandRepository brandRepository;
	final private ProductRepository productRepository;
	final private CampaignRepository campaignRepository;

	public ServiceResult<Menu> getMenu(String brandName) {
		ServiceResult<Menu> serviceResult = new ServiceResult<>();

		try {
			Brand brand = brandRepository.findByName(brandName);

			if (!brand.getFeatures().contains(FeatureType.QR_MENU)) {
				serviceResult.setHttpStatus(HttpStatus.FORBIDDEN);
				serviceResult.setMessage("Entity can not save. Error message: Required privilege not defined!");
				return serviceResult;
			}
			List<Category> categories = categoryRepository.findAllByBrand(brand);
			categories = categories.stream().sorted(Comparator.comparing(Category::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			for (Category category : categories) {
				List<Product> products = productRepository.findAllByCategoryId(category.getId());
				products = products.stream().sorted(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
				products.forEach(product -> {
					product.setCategory(null);
					product.setCategoryName(category.getName());
				});
				category.setProducts(products);
			}
			List<Campaign> campaigns = campaignRepository.findAllByBrand(brand);
			campaigns = campaigns.stream().sorted(Comparator.comparing(Campaign::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			serviceResult.setValue(new Menu(categories, campaigns, brand));
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}

	public ServiceResult<Boolean> like(LikeRequest dto) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<>();

		try {
			Product product = productRepository.getOne(dto.getProductId());
			Brand brand = product.getCategory().getBrand();
			if (!brand.getFeatures().contains(FeatureType.LIKE)) {
				serviceResult.setHttpStatus(HttpStatus.FORBIDDEN);
				serviceResult.setMessage("Entity can not save. Error message: Required privilege not defined!");
				return serviceResult;
			}

			if (dto.getLike()) {
				product.setLikes(product.getLikes() + 1);
			} else if (product.getLikes() > 0) {
				product.setLikes(product.getLikes() - 1);
			}
			productRepository.save(product);
			serviceResult.setValue(true);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setValue(false);
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}

	public ServiceResult<List<Campaign>> getCampaigns(String brandName) {
		ServiceResult<List<Campaign>> serviceResult = new ServiceResult<>();

		try {
			Brand brand = brandRepository.findByName(brandName);
			if (!brand.getFeatures().contains(FeatureType.CAMPAIGN)) {
				serviceResult.setHttpStatus(HttpStatus.FORBIDDEN);
				serviceResult.setMessage("Entity can not save. Error message: Required privilege not defined!");
				return serviceResult;
			}
			List<Campaign> campaigns = campaignRepository.findAllByBrand(brand);
			campaigns = campaigns.stream().sorted(Comparator.comparing(Campaign::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			serviceResult.setValue(campaigns);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
