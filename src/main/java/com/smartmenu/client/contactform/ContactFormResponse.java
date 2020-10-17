package com.smartmenu.client.contactform;

import com.smartmenu.common.basemodel.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactFormResponse extends BaseResponse {
	private String fullName;
	private String phoneNumber;
	private String subject;
	private String message;
}
