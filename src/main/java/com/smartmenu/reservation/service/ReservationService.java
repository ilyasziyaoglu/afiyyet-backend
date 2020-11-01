package com.smartmenu.reservation.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.model.FeatureType;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.client.reservation.ReservationRequest;
import com.smartmenu.client.reservation.ReservationResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.reservation.db.entity.Reservation;
import com.smartmenu.reservation.db.repository.ReservationRepository;
import com.smartmenu.reservation.mapper.ReservationMapper;
import com.smartmenu.reservation.mapper.ReservationUpdateMapper;
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
public class ReservationService extends AbstractBaseService<ReservationRequest, Reservation, ReservationResponse, ReservationMapper> {
	final private ReservationRepository repository;
	final private ReservationMapper mapper;
	final private ReservationUpdateMapper updateMapper;
	final private BrandRepository brandRepository;

	@Override
	public ReservationRepository getRepository() {
		return repository;
	}

	@Override
	public ReservationMapper getMapper() {
		return mapper;
	}

	@Override
	public ReservationUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<Boolean> save(ReservationRequest dto) {
		Brand brand = brandRepository.getOne(dto.getBrand().getId());
		try {
			if (!brand.getFeatures().contains(FeatureType.RESERVATIONS)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			Reservation entityToSave = getMapper().toEntity(dto);
			entityToSave.setBrand(brand);
			getRepository().save(entityToSave);
			return new ServiceResult<>(true, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}

	public ServiceResult<List<Reservation>> getReservationsByBrand(String token) {
		try {
			if (!getUser(token).getBrand().getFeatures().contains(FeatureType.ORDERING)) {
				return new ServiceResult<>(HttpStatus.FORBIDDEN, "Entity can not save. Error message: Required privilege not defined!");
			}
			List<Reservation> entityList = getRepository().findAllByBrandAndReservationDateAfter(getUser(token).getBrand(), ZonedDateTime.now());
			entityList = entityList.stream().sorted(Comparator.comparing(Reservation::getReservationDate, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			return new ServiceResult<>(entityList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
}
