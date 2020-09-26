package com.smartmenu.reservation.db.entity;


import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@EqualsAndHashCode(callSuper = true)
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
		return this.id;
	}
}
