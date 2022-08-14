package com.afiyyet.reservation.db.repository;

import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.repository.BaseRepository;
import com.afiyyet.reservation.db.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Repository
public interface ReservationRepository extends BaseRepository<Reservation> {
	List<Reservation> findAllByBrandAndReservationDateAfter(Brand brand, ZonedDateTime reservationDate);
}
