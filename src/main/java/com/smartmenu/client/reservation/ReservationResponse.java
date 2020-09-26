package com.smartmenu.client.reservation;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ReservationResponse extends BaseResponse {

	private String fullName;
	private ZonedDateTime reservationDate;
	private BrandResponse brand;
	private Integer childCount;
	private Integer adultCount;
	private String phoneNumber;
	private String specifications;
	private Status status = Status.ACTIVE;
}
