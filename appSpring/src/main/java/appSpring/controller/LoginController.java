package appSpring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import appSpring.repository.UserRepository;
import appSpring.entity.User;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {

		if (request.isUserInRole("ADMIN") || request.isUserInRole("USER"))
			return "redirect:/";
		model.addAttribute("unlogged", true);
		model.addAttribute("profile", true);

		return "login";
	}

	@RequestMapping("/loginError")
	public String loginError(Model model) {

		model.addAttribute("unlogged", true);
		model.addAttribute("loginError", true);
		model.addAttribute("profile", true);

		return "login";
	}

	@RequestMapping("/register")
	public String register(Model model, HttpServletRequest request) {

		if (request.isUserInRole("ADMIN") || request.isUserInRole("USER"))
			return "redirect:/";
		model.addAttribute("unlogged", true);

		return "register";
	}

	@RequestMapping("/register/add")
	public String addUserAction(@RequestParam String name, @RequestParam String password, @RequestParam String dni,
			@RequestParam String firstName, @RequestParam String lastName1, @RequestParam String lastName2,
			@RequestParam String email, @RequestParam String telephone) {

		User user = new User(name, password, dni, firstName, lastName1, lastName2, email, telephone, "ROLE_USER");
		try {
			userRepository.save(user);
		} catch (Exception e) {
			return "redirect:/registerError";
		}

		return "redirect:/";
	}

	@RequestMapping("/registerError")
	public String registerError(Model model) {

		model.addAttribute("unlogged", true);
		model.addAttribute("alreadyReg", true);

		return "register";
	}

}