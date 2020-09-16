package com.smartmenu.common.auth.request;

import com.smartmenu.client.user.Gender;
import lombok.Data;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-25
 */

@Data
public class RegisterRequest {
	private String email;
	private String password;
	private String fullName;
	private Gender gender;
}
