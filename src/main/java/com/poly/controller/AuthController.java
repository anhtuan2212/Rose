package com.poly.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.dao.AccountDAO;
import com.poly.entity.Account;
import com.poly.service.AccountService;

import net.bytebuddy.utility.RandomString;

@Controller
public class AuthController {

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	AccountService accountService;


	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/rest/auth/authentication")
	public Object getAuthentication(HttpSession session) {
		return session.getAttribute("authentication");
	}

	@RequestMapping("/auth/login/form")
	public String logInForm(Model model, @ModelAttribute("account") Account account) {
		return "auth/login";
	}

	@RequestMapping("/auth/login/success")
	public String logInSuccess(Model model, @ModelAttribute("account") Account account) {
		model.addAttribute("message", "Đăng Nhập Thành Công !");
		return "redirect:/index";
	}

	@RequestMapping("/auth/login/error")
	public String logInError(Model model, @Validated @ModelAttribute("account") Account account, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Lỗi Đăng Nhập !");
			return "auth/login";
		}
		return "auth/login";
	}

	@RequestMapping("/auth/unauthoried")
	public String unauthoried(Model model, @ModelAttribute("account") Account account) {
		model.addAttribute("message", "Bạn không thể truy cập !");
		return "auth/login";
	}

	@RequestMapping("/auth/logout/success")
	public String logOutSuccess(Model model, @ModelAttribute("account") Account account) {
		model.addAttribute("message", "Đăng Xuất Thành Công !");
		return "auth/login";
	}

	// OAuth2
	@RequestMapping("/oauth2/login/success")
	public String oauth2(OAuth2AuthenticationToken oauth2) {
		accountService.loginFromOAuth2(oauth2);
		return "forward:/auth/login/success";
	}

	public String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
