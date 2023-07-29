package com.ashokit.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockAccForm;
import com.ashokit.entity.UserDetailsEntity;
import com.ashokit.repository.UserDetailsRepo;
import com.ashokit.utility.EmailUtils;
import com.ashokit.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDetailsRepo userRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean signUp(SignUpForm signUp) {
		/*validate user with email is already available or not, if available, 
		dont create account with given email,if not, create account */
		UserDetailsEntity entity = userRepo.findByEmail(signUp.getEmail());
		if(entity!=null) {
			return false;
		}
		
		//todo-2: copy form obj to entity obj
		UserDetailsEntity userEntity=new UserDetailsEntity();
		BeanUtils.copyProperties(signUp, userEntity);
		
		//todo-3: generate random password and set to entity object
		String pwd = PwdUtils.generatePassword();
		userEntity.setPwd(pwd);
		
		//todo-4: set accStatus to lock
		userEntity.setAccStatus("LOCKED");
		
		//todo-5: insert record into db
		userRepo.save(userEntity);
		
		//todo-6: send email
		String to = signUp.getEmail();
		String subject = "unlock your account";
		StringBuffer body = new StringBuffer();

		body.append("temporary password: " + pwd);
		body.append("<br>");
		body.append("<h2>use below temporary password to unlock your account</h2>");
		body.append("<br>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">click here to unlock your account");
		emailUtils.sendEmail(to, subject, body.toString());
		
		return true;
	}


	@Override
	public String login(LoginForm login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean unlockAcc(UnlockAccForm unlock) {
		return null;
	}

	@Override
	public String forgotPwd(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
