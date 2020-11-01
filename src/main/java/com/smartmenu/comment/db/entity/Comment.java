package com.smartmenu.comment.db.entity;


import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@Entity
@Table(name = "comments")
@EqualsAndHashCode(callSuper = true)
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
		return this.id;
	}
}
