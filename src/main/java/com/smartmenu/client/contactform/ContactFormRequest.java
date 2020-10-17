package com.smartmenu.client.contactform;

import com.smartmenu.common.basemodel.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactFormRequest extends BaseRequest {
	private String fullName;
	private String phoneNumber;
	private String subject;
	private String message;
}
