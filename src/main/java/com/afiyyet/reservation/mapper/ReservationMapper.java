package com.afiyyet.reservation.mapper;

import com.afiyyet.client.reservation.ReservationRequest;
import com.afiyyet.client.reservation.ReservationResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.reservation.db.entity.Reservation;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface ReservationMapper extends BaseMapper<ReservationRequest, Reservation, ReservationResponse> {
}
