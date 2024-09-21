package com.afiyyet.reservation.mapper;

import com.afiyyet.brand.mapper.BrandMapper;
import com.afiyyet.client.reservation.ReservationRequest;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import com.afiyyet.reservation.db.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class ReservationUpdateMapper implements BaseUpdateMapper<ReservationRequest, Reservation> {

	private final BrandMapper brandMapper;

	@Override
	public Reservation toEntityForUpdate(ReservationRequest request, Reservation entity) {
		if (entity == null) {
			entity = new Reservation();
		}

		if (request.getFullName() != null) {
			entity.setFullName(request.getFullName());
		}
		if (request.getReservationDate() != null) {
			entity.setReservationDate(request.getReservationDate());
		}
		if (request.getBrand() != null) {
			entity.setBrand(brandMapper.toEntity(request.getBrand()));
		}
		if (request.getChildCount() != null) {
			entity.setChildCount(request.getChildCount());
		}
		if (request.getAdultCount() != null) {
			entity.setAdultCount(request.getAdultCount());
		}
		if (request.getPhoneNumber() != null) {
			entity.setPhoneNumber(request.getPhoneNumber());
		}
		if (request.getSpecifications() != null) {
			entity.setSpecifications(request.getSpecifications());
		}
		if (request.getStatus() != null) {
			entity.setStatus(request.getStatus());
		}

		return entity;
	}
}
