package ca.sait.backup.controller.html.user.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/support")
public class SupportController {

    @GetMapping("/home")
    public String GetSupportHome() {
        return "/user/support.html";
    }

}
