package com.smartmenu.campaign.db.entity;


import com.smartmenu.category.db.entity.Category;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.common.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@Entity
@Table(name = "campaigns")
@EqualsAndHashCode(callSuper = true)
public class Campaign extends AbstractBaseEntity {

	private static final long serialVersionUID = -6997084654327883455L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "campaign_id_gen", sequenceName = "campaign_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "campaign_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "img_url")
	private String imgUrl;

	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "description", length = 2000)
	private String description;

	@Column(name = "likes")
	private Integer likes;

	@Column(name = "order_value")
	private Integer order;

	@Column(name = "status")
	private Status status;

	@Column(name = "expire_date")
	private ZonedDateTime expireDate;

	@Column(name = "start_date")
	private ZonedDateTime startDate;

	@Override
	public Long getId() {
		return this.id;
	}
}
