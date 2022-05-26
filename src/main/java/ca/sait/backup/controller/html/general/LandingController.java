package ca.sait.backup.controller.html.general;


import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.sait.backup.config.auth.PrincipalDetails;



@Slf4j
@Controller
@RequestMapping("/general")
public class LandingController {

    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("Login triggered");
        return "general/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        System.out.println("Register triggered");
        return "general/register";
    }
    //google login
    @GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("Principal : " + principal);
		System.out.println("OAuth2 : "+principal.getUser().getProvider());
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}

		return "Success User";
	}
}