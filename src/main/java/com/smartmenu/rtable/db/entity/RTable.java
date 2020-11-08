package com.smartmenu.rtable.db.entity;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.common.basemodel.db.entity.AbstractBaseEntity;
import com.smartmenu.order.db.entity.Order;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Data
@Entity
@Table(name = "tables")
@EqualsAndHashCode(callSuper = true)
public class RTable extends AbstractBaseEntity {

	private static final long serialVersionUID = 2818358983516690819L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "rtables_id_gen", sequenceName = "rtables_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "rtables_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@OneToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "is_open")
	private Boolean isOpen;

	@Override
	public Long getId() {
		return this.id;
	}
}
