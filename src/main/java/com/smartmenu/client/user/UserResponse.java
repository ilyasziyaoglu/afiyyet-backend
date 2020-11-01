package com.smartmenu.client.user;

import com.smartmenu.client.brand.BrandResponse;
import com.smartmenu.common.basemodel.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends BaseResponse {

	private String username;
	private String fullName;
	private BrandResponse brand;
	private String imageUrl;
	private String email;
	private String phone;
	private String addresses;
	private String roles;
	private Gender gender;
	private Date birthdate;
	private String country;
	private Date date;
}
