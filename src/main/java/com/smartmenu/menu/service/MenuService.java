package com.smartmenu.menu.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.db.repository.CampaignRepository;
import com.smartmenu.category.db.entity.Category;
import com.smartmenu.category.db.repository.CategoryRepository;
import com.smartmenu.category.service.CategoryService;
import com.smartmenu.client.brand.BrandRequest;
import com.smartmenu.client.menu.LikeRequest;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.product.db.entity.Product;
import com.smartmenu.product.db.repository.ProductRepository;
import com.smartmenu.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public ServiceResult<List<Category>> getMenu(String brandName) {
		ServiceResult<List<Category>> serviceResult = new ServiceResult<>();

		try {
			Brand brand = brandRepository.findByName(brandName);
			List<Category> categoryList = categoryRepository.findAllByBrand(brand);
			for (Category category : categoryList) {
				List<Product> products = productRepository.findAllByCategoryId(category.getId());
				products.forEach(product -> product.setCategory(null));
				category.setProducts(products);
			}
			serviceResult.setValue(categoryList);
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
			serviceResult.setValue(campaignRepository.findAllByBrand(brand));
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
