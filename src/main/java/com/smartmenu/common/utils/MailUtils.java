package com.smartmenu.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class MailUtils {

	public final static String MAIL_SUBJECT_KEY = "mail.subject";

	public static boolean validateEmailByRegex(String email) {
		try {
			if (StringUtils.isEmpty(email)) {
				return false;
			}
			String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(emailPattern);
			return pattern.matcher(email).matches();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
