package com.ashokit.service;

import javax.servlet.http.HttpSession;

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
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDetailsRepo userRepo;

	@Autowired
	private EmailUtils myEmail;

	@Autowired
	private HttpSession session;

	@Override
	public boolean signUp(SignUpForm form) {

		// check for unique email
		UserDetailsEntity user = userRepo.findByEmail(form.getEmail());
		if (user != null) {
			return false;
		}

		// TODO: copy data from binding obj to entity obj
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(form, entity);

		// TODO: generate randome password and set to object
		String randomPwd = PwdUtils.generatePassword();
		entity.setPwd(randomPwd);

		// TODO: set accc status to locked
		entity.setAccStatus("LOCKED");

		// TODO: insert record into database
		userRepo.save(entity);

		// TODO: send email
		String to = form.getEmail();
		String subject = "unlock your account";
		StringBuffer body = new StringBuffer();

		body.append("temporary password: " + randomPwd);
		body.append("<br>");
		body.append("<h2>use below link to unlock your account</h2>");
		body.append("<br>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">click here to unlock your account");
		myEmail.sendEmail(to, subject, body.toString());

		return true;
	}

	@Override
	public boolean unlockAcc(UnlockAccForm form) {
		UserDetailsEntity entity = userRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			userRepo.save(entity);
			return true;
		}else {
		return false;
		}
	}

	@Override
	public String login(LoginForm form) {
		UserDetailsEntity entity = userRepo.findByEmailAndPwd(form.getEmail(), form.getPassword());
		if(entity==null) {
			return "Invalid Credential";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "your account is locked, first unlocked your account and then try for login";
		}
		
		// create session object and store userid into session
		session.setAttribute("userId", entity.getUserId());
		
		return "success";
	}

	@Override
	public boolean forgotPwd(String email) {
		try {

			// TODO: retrive record based on email
			UserDetailsEntity entity = userRepo.findByEmail(email);
			// TODO: check record available or not
			if (entity == null) {
				return false;
			}
			// TODO: write body for email
			String subject = "Recovered email";
			StringBuffer body = new StringBuffer();
			body.append("Your Password is- " + entity.getPwd());
			body.append("<br>");
			body.append("<a href=\"http://localhost:9090/login\"><h3>click here to login</h3>");

			// TODO: send email
			myEmail.sendEmail(email, subject, body.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	

}
