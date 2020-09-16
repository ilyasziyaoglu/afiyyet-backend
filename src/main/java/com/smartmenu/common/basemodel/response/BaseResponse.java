package com.smartmenu.common.basemodel.response;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public abstract class BaseResponse {

	private Long id;

	public BaseResponse() {
	}

	public BaseResponse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
