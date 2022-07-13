package ca.sait.backup.controller.html.user.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/settings")
public class SettingsController {

    @GetMapping("/home")
    public String GetSettingsHome() {
        return "/user/settings_profile.html";
    }

    @GetMapping("/security")
    public String GetSettingsSecurity() {
        return "/user/settings_security.html";
    }

    @GetMapping("/delete")
    public String GetSettingsDelete() {
        return "/user/settings_delete.html";
    }

}
