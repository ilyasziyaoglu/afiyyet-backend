package com.afiyyet.client.comment;

import com.afiyyet.client.brand.BrandResponse;
import com.afiyyet.common.basemodel.response.BaseResponse;
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
public class CommentResponse extends BaseResponse {
	private String comment;
	private BrandResponse brand;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BrandResponse getBrand() {
		return brand;
	}

	public void setBrand(BrandResponse brand) {
		this.brand = brand;
	}
}
