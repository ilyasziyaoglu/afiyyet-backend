package com.afiyyet.client.reservation;

import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
import com.afiyyet.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ZonedDateTime getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(ZonedDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	public BrandResponse getBrand() {
		return brand;
	}

	public void setBrand(BrandResponse brand) {
		this.brand = brand;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Integer getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(Integer adultCount) {
		this.adultCount = adultCount;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
