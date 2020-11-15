package com.smartmenu.reservation.controller;

import com.smartmenu.client.reservation.ReservationRequest;
import com.smartmenu.client.reservation.ReservationResponse;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.reservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/reservation")
public class ReservationController extends AbstractBaseController {
	private ReservationService service;

	public ReservationController(final ReservationService service) {
		this.service = service;
	}

	public ReservationService getService() {
		return service;
	}

	@GetMapping("/get-reservations-by-brand")
	public ResponseEntity<ServiceResult<List<ReservationResponse>>> getReservationsByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<ReservationResponse>> serviceResult = getService().getReservationsByBrand(token);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/reserve")
	public ResponseEntity<ServiceResult<Boolean>> insert(@RequestBody ReservationRequest request) {
		ServiceResult<Boolean> serviceResult = getService().insert(request);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
