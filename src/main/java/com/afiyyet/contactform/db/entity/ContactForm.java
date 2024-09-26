package com.afiyyet.contactform.db.entity;


import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
