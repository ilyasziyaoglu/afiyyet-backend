package com.afiyyet.brand.db.entity;


import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.enums.Status;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
public class Brand extends AbstractBaseEntity {

	private static final long serialVersionUID = 9124949861592919633L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "brands_id_gen", sequenceName = "brands_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "brands_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "unique_name", unique = true)
	private String uniqueName;


	@Column(name = "logo_img_url")
	private String logoImgUrl;

	@Column(name = "status")
	private Status status;

	@ElementCollection
	@CollectionTable(name = "brand_features", joinColumns = @JoinColumn(name = "brand_id"))
	@Column(name = "feature")
	private Set<FeatureType> features = new HashSet<>();

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getLogoImgUrl() {
		return logoImgUrl;
	}

	public void setLogoImgUrl(String logoImgUrl) {
		this.logoImgUrl = logoImgUrl;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<FeatureType> getFeatures() {
		return features;
	}

	public void setFeatures(Set<FeatureType> features) {
		this.features = features;
	}
}
