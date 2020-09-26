package com.smartmenu.reservation.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.reservation.db.entity.Reservation;
import com.smartmenu.reservation.db.repository.ReservationRepository;
import com.smartmenu.reservation.mapper.ReservationMapper;
import com.smartmenu.reservation.mapper.ReservationUpdateMapper;
import com.smartmenu.client.reservation.ReservationRequest;
import com.smartmenu.client.reservation.ReservationResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
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

	@Override
	public ServiceResult<Reservation> save(String token, ReservationRequest request) {
		ServiceResult<Reservation> serviceResult = new ServiceResult<>();
		try {
			Reservation entityToSave = getMapper().toEntity(request);
			Brand brand = brandRepository.getOne(request.getBrand().getId());
			entityToSave.setBrand(brand);
			Reservation entity = getRepository().save(entityToSave);
			serviceResult.setValue(entity);
			serviceResult.setHttpStatus(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setMessage("Entity can not save. Error message: " + e.getMessage());
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return serviceResult;
	}

	public ServiceResult<List<Reservation>> getReservationsByBrand(String token) {
		ServiceResult<List<Reservation>> serviceResult = new ServiceResult<>();
		try {
			List<Reservation> entityList = getRepository().findAllByBrandAndReservationDateAfter(getUser(token).getBrand(), ZonedDateTime.now());
			entityList = entityList.stream().sorted(Comparator.comparing(Reservation::getReservationDate, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
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
