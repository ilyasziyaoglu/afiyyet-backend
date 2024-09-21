package com.afiyyet.client.brand;

import com.afiyyet.brand.db.model.FeatureType;
import com.afiyyet.common.basemodel.request.BaseRequest;
import com.afiyyet.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BrandRequest extends BaseRequest {

	private String name;
	private String uniqueName;
	private String logoImgUrl;
	private Status status;
	private Set<FeatureType> features;

}
