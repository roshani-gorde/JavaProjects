package com.ashokit.service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockAccForm;

public interface UserService {

	public boolean signUp(SignUpForm form);

	public boolean unlockAcc(UnlockAccForm form);
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);
}
