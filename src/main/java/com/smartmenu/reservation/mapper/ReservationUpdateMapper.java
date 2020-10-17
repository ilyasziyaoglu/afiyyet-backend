package com.smartmenu.reservation.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.client.reservation.ReservationRequest;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
import com.smartmenu.reservation.db.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class ReservationUpdateMapper implements BaseUpdateMapper<ReservationRequest, Reservation> {

	final private BrandMapper brandMapper;

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
