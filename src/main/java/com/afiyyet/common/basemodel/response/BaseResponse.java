package com.afiyyet.common.basemodel.response;

import java.time.ZonedDateTime;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public abstract class BaseResponse {

	private Long id;

	private ZonedDateTime createdDate;

	public BaseResponse() {}

	public BaseResponse(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public BaseResponse(Long id, ZonedDateTime createdDate) {
		this.id = id;
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}
}
