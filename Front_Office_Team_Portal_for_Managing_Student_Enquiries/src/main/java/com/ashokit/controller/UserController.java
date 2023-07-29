package com.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ashokit.binding.LoginForm;
import com.ashokit.binding.SignUpForm;
import com.ashokit.binding.UnlockAccForm;
import com.ashokit.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String loadLoginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String handleLoginFun(@ModelAttribute("loginForm") LoginForm form, Model model) {
		String status = userService.login(form);
		if(status.contains("success")) {
			return "redirect:/dashboard";	
		}else {
			model.addAttribute("errMsg", status);
		}
		return "login";
	}
	
	// to load empty signup form page
	@GetMapping("/signup")
	public String loadSignUpPage(Model model) {
		model.addAttribute("signUpForm",new SignUpForm());
		return "signup";
	}
	
	// to handle signUp functionality 
	@PostMapping("/signup")
	public String handleSignUpFun(@ModelAttribute("signUpForm") SignUpForm form,Model model) {
		boolean status = userService.signUp(form);
		if(status) {
			model.addAttribute("succMsg", "Registration Successfull");
		}else {
			model.addAttribute("errorMsg", "try With Different Email");
		}
		return "signup";
		}


	@GetMapping("/forgotPwd")
	public String loadForgotPwdPage(Model model) {
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgetPwdFun(@RequestParam("email") String email, Model model) {
		System.out.println(email);
		try {
			boolean status = userService.forgotPwd(email);
			if (status) {
				model.addAttribute("succMsg", "password recovred successfuly, Please check your email");
			} else {
				model.addAttribute("errMsg", "Invalid Email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "forgotPwd";
	}

	@GetMapping("/unlock")
	public String loadUnlockPage(@RequestParam String email,Model model) {
		UnlockAccForm unlockForm=new UnlockAccForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlockForm", unlockForm);
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String handleUnlockAccFun(@ModelAttribute("unlockForm") UnlockAccForm form,Model model ) {
		if(form.getNewPwd().equals(form.getConfirmNewPwd())) {
			boolean status = userService.unlockAcc(form);
			if(status) {
				model.addAttribute("succMsg", "your account unlocked sucessfully, please login");
			}else {
				model.addAttribute("errMsg", "your temporary password is incorrect");
			}
		}else {
			model.addAttribute("errMsg", "your new and confirm password are incorrect, Try again");
		}
		return "unlock";
	}


}
