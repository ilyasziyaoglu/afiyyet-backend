package com.afiyyet.client.brand;

import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.common.basemodel.response.BaseResponse;
import com.afiyyet.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BrandResponse extends BaseResponse {

	private String name;
	private String uniqueName;
	private String logoImgUrl;
	private Status status;
	private Set<FeatureType> features;

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
