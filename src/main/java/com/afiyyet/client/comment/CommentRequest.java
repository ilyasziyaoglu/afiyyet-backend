package com.afiyyet.client.comment;

import com.afiyyet.client.brand.BrandRequest;
import com.afiyyet.common.basemodel.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentRequest extends BaseRequest {
	private String comment;
	private BrandRequest brand;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BrandRequest getBrand() {
		return brand;
	}

	public void setBrand(BrandRequest brand) {
		this.brand = brand;
	}
}
