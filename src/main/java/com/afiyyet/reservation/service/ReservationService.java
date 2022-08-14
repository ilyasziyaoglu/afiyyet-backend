package com.afiyyet.reservation.service;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.brand.db.repository.BrandRepository;
import com.afiyyet.client.reservation.ReservationRequest;
import com.afiyyet.client.reservation.ReservationResponse;
import com.afiyyet.common.basemodel.service.AbstractBaseService;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.reservation.db.entity.Reservation;
import com.afiyyet.reservation.db.repository.ReservationRepository;
import com.afiyyet.reservation.mapper.ReservationMapper;
import com.afiyyet.reservation.mapper.ReservationUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

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

	public ServiceResult<Boolean> insert(ReservationRequest dto) {
		Brand brand = brandRepository.getOne(dto.getBrand().getId());
		try {
			if (!brand.getFeatures().contains(FeatureType.RESERVATIONS)) {
				return forbiddenBoolean();
			}
			Reservation entityToSave = getMapper().toEntity(dto);
			entityToSave.setBrand(brand);
			getRepository().save(entityToSave);
			return new ServiceResult<>(true, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}

	public ServiceResult<List<ReservationResponse>> getReservationsByBrand(String token) {
		try {
			if (!getUser(token).getBrand().getFeatures().contains(FeatureType.ORDERING)) {
				return forbiddenList();
			}
			List<Reservation> entityList = getRepository().findAllByBrandAndReservationDateAfter(getUser(token).getBrand(), ZonedDateTime.now());
			entityList.sort(Comparator.comparing(Reservation::getReservationDate, Comparator.nullsLast(Comparator.naturalOrder())));
			return new ServiceResult<>(mapper.toResponse(entityList));
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
