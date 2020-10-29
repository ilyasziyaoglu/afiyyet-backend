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

	public ServiceResult<Menu> getMenu(String brandUniqueName) {
		try {
			Brand brand = brandRepository.findByUniqueName(brandUniqueName);

			List<Category> categories = categoryRepository.findAllByBrandId(brand.getId());
			categories = categories.stream().sorted(Comparator.comparing(Category::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			for (Category category : categories) {
				category.getProducts().sort(Comparator.comparing(Product::getOrder, Comparator.nullsLast(Comparator.naturalOrder())));
			}
			List<Campaign> campaigns = campaignRepository.findAllByBrandId(brand.getId());
			campaigns = campaigns.stream().sorted(Comparator.comparing(Campaign::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			return new ServiceResult<>(new Menu(categories, campaigns, brand), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<Boolean> productLike(LikeRequest dto) {
		try {
			Product product = productRepository.getOne(dto.getItemId());
			Brand brand = brandRepository.getOne(product.getCategory().getBrandId());
			if (!brand.getFeatures().contains(FeatureType.LIKE)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}

			if (dto.getLike()) {
				product.setLikes(product.getLikes() + 1);
			} else if (product.getLikes() > 0) {
				product.setLikes(product.getLikes() - 1);
			}
			productRepository.save(product);
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<Boolean> campaignLike(LikeRequest dto) {
		try {
			Campaign campaign = campaignRepository.getOne(dto.getItemId());
			Brand brand = brandRepository.getOne(campaign.getBrandId());
			if (!brand.getFeatures().contains(FeatureType.LIKE)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}

			if (dto.getLike()) {
				campaign.setLikes(campaign.getLikes() + 1);
			} else if (campaign.getLikes() > 0) {
				campaign.setLikes(campaign.getLikes() - 1);
			}
			campaignRepository.save(campaign);
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(false, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<List<Campaign>> getCampaigns(String brandUniqueName) {

		try {
			Brand brand = brandRepository.findByUniqueName(brandUniqueName);
			if (!brand.getFeatures().contains(FeatureType.CAMPAIGN)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			List<Campaign> campaigns = campaignRepository.findAllByBrandId(brand.getId());
			campaigns = campaigns.stream().sorted(Comparator.comparing(Campaign::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());

			return new ServiceResult<>(campaigns, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
