package com.afiyyet.common.basemodel.request;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

public abstract class BaseRequest {

	private Long id;

	public BaseRequest() {
	}

	public BaseRequest(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
