package com.ashokit.service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockAccForm;

public interface UserService {
	
	public boolean signUp(SignUpForm signUp);
	 
	public String login(LoginForm login);
	
	public boolean unlockAcc(UnlockAccForm unlock);
	
	public String forgotPwd(String email);

}
