package com.smartmenu.common.auth.request;

import lombok.Data;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-24
 */

@Data
public class LoginRequest {
	private String email;
	private String password;
}
