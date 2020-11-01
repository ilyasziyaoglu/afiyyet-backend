package com.smartmenu.campaign.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.model.FeatureType;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.db.repository.CampaignRepository;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.campaign.mapper.CampaignUpdateMapper;
import com.smartmenu.client.campaign.CampaignRequest;
import com.smartmenu.client.campaign.CampaignResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
public class CampaignService extends AbstractBaseService<CampaignRequest, Campaign, CampaignResponse, CampaignMapper> {
	final private CampaignRepository repository;
	final private CampaignMapper mapper;
	final private CampaignUpdateMapper updateMapper;
	final private BrandRepository brandRepository;

	@Override
	public CampaignRepository getRepository() {
		return repository;
	}

	@Override
	public CampaignMapper getMapper() {
		return mapper;
	}

	@Override
	public CampaignUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<Campaign> save(String token, CampaignRequest request) {
		try {
			Brand brand = brandRepository.getOne(getUser(token).getBrand()
					.getId());
			if (!brand.getFeatures()
					.contains(FeatureType.CAMPAIGN)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			Campaign entityToSave = getMapper().toEntity(request);
			entityToSave.setLikes(0);
			entityToSave.setBrandId(brand.getId());
			Campaign entity = getRepository().save(entityToSave);
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	public ServiceResult<Boolean> arrangeCampaigns(String token, Map<Long, Integer> dto) {
		User user = getUser(token);
		if (user.getBrand()
				.getFeatures()
				.contains(FeatureType.CAMPAIGN)) {
			return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
		}

		try {
			Optional<List<Campaign>> entityList = Optional.of(getRepository().findAllById(dto.keySet()));
			for (Campaign entity : entityList.get()) {
				if (user.getBrand().getId().equals(entity.getBrandId())) {
					entity.setOrder(dto.get(entity.getId()));
					getRepository().save(entity);
				}
			}
			return new ServiceResult<>(true, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	public ServiceResult<List<Campaign>> getCampaigns(String token) {
		try {
			if (!getUser(token).getBrand()
					.getFeatures()
					.contains(FeatureType.CAMPAIGN)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			List<Campaign> entityList = getRepository().findAllByBrandId(getUser(token).getBrand().getId());
			entityList = entityList.stream()
					.sorted(Comparator.comparing(Campaign::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
					.collect(Collectors.toList());
			return new ServiceResult<>(entityList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
