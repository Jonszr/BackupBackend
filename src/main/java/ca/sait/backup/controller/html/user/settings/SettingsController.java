package ca.sait.backup.controller.html.user.settings;

import ca.sait.backup.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/user/settings")
public class SettingsController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/home")
    public String GetSettingsHome(Model model, HttpServletRequest request) {
        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);
        return "/user/settings_profile.html";
    }

    @GetMapping("/security")
    public String GetSettingsSecurity(Model model, HttpServletRequest request) {
        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);
        return "/user/settings_security.html";
    }

    @GetMapping("/delete")
    public String GetSettingsDelete(Model model, HttpServletRequest request) {
        // Expose session variables
        this.sessionService.exposeEssentialVariables(request, model);
        return "/user/settings_delete.html";
    }

}
