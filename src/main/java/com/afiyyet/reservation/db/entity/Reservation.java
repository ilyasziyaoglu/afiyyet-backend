package com.afiyyet.reservation.db.entity;


import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Entity
@Table(name = "reservations")
public class Reservation extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "reservations_id_gen", sequenceName = "reservations_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "reservations_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "reservation_date")
	private ZonedDateTime reservationDate;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Column(name = "child_count")
	private Integer childCount;

	@Column(name = "adult_count")
	private Integer adultCount;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Column(name = "specifications")
	private String specifications;

	@Column(name = "status")
	private Status status = Status.ACTIVE;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
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
