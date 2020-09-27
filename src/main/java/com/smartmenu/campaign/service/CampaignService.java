package com.smartmenu.campaign.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.campaign.db.entity.Campaign;
import com.smartmenu.campaign.db.repository.CampaignRepository;
import com.smartmenu.campaign.mapper.CampaignMapper;
import com.smartmenu.campaign.mapper.CampaignUpdateMapper;
import com.smartmenu.client.brand.BrandRequest;
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
	final private BrandMapper brandMapper;
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

	@Override
	public ServiceResult<Campaign> save(String token, CampaignRequest request) {
		ServiceResult<Campaign> serviceResult = new ServiceResult<>();
		try {
			Campaign entityToSave = getMapper().toEntity(request);
			Brand brand = brandRepository.getOne(getUser(token).getBrand().getId());
			entityToSave.setBrand(brand);
			Campaign entity = getRepository().save(entityToSave);
			serviceResult.setValue(entity);
			serviceResult.setHttpStatus(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setMessage("Entity can not save. Error message: " + e.getMessage());
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return serviceResult;
	}

	public ServiceResult<Boolean> arrangeCampaigns(String token, Map<Long, Integer> dto) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<>();

		User user = getUser(token);

		try {
			Optional<List<Campaign>> entityList = Optional.of(getRepository().findAllById(dto.keySet()));
			for (Campaign entity : entityList.get()) {
				if (user.getBrand().equals(entity.getBrand())) {
					entity.setOrder(dto.get(entity.getId()));
					getRepository().save(entity);
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

	public ServiceResult<List<Campaign>> getCampaigns(String token) {
		ServiceResult<List<Campaign>> serviceResult = new ServiceResult<>();
		try {
			List<Campaign> entityList = getRepository().findAllByBrand(getUser(token).getBrand());
			entityList = entityList.stream().sorted(Comparator.comparing(Campaign::getOrder, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			serviceResult.setValue(entityList);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}

}
