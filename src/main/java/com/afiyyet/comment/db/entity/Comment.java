package com.afiyyet.comment.db.entity;


import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;

import javax.persistence.*;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Entity
@Table(name = "comments")
public class Comment extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "comments_id_gen", sequenceName = "comments_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "comments_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "comment", length = 2000)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
