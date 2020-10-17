package com.smartmenu.contactform.db.entity;


import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "contactforms")
public class ContactForm extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "contactforms_id_gen", sequenceName = "contactforms_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "contactforms_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "phone_number", length = 15)
	private String phoneNumber;

	@Column(name = "subject")
	private String subject;

	@Column(name = "message", length = 2000)
	private String message;

	@Override
	public Long getId() {
		return this.id;
	}
}
