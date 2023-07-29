package com.ashokit.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PwdUtils {
	
	public static String generatePassword() {
	int keyLength = 10;
	String pwd = RandomStringUtils.randomAlphabetic(keyLength);
	return pwd;
	}
}
