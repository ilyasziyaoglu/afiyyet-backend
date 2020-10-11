package com.smartmenu.reservation.controller;

import com.smartmenu.reservation.db.entity.Reservation;
import com.smartmenu.reservation.mapper.ReservationMapper;
import com.smartmenu.reservation.service.ReservationService;
import com.smartmenu.client.reservation.ReservationRequest;
import com.smartmenu.client.reservation.ReservationResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/reservation")
public class ReservationController extends AbstractBaseController<ReservationRequest, Reservation, ReservationResponse, ReservationMapper, ReservationService> {
	private ReservationService service;
	private ReservationMapper mapper;

	public ReservationController(final ReservationService service, final ReservationMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public ReservationService getService() {
		return service;
	}

	public ReservationMapper getMapper() {
		return mapper;
	}

	@GetMapping("/get-reservations-by-brand")
	public ResponseEntity<List<Reservation>> getReservationsByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<Reservation>> serviceResult = getService().getReservationsByBrand(token);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("/reserve")
	public ResponseEntity<Boolean> insert(@RequestBody ReservationRequest request) {
		ServiceResult<Boolean> serviceResult = getService().save(request);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
