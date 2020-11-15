package com.smartmenu.reservation.mapper;

import com.smartmenu.client.reservation.ReservationRequest;
import com.smartmenu.client.reservation.ReservationResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import com.smartmenu.reservation.db.entity.Reservation;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface ReservationMapper extends BaseMapper<ReservationRequest, Reservation, ReservationResponse> {
}
