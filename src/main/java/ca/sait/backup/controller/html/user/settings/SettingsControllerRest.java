package ca.sait.backup.controller.html.user.settings;


import ca.sait.backup.model.request.ChangePasswordRequest;
import ca.sait.backup.model.request.UpdateProfileDetailsRequest;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/pri/user/settings")
public class SettingsControllerRest {

    @Autowired
    private UserService userService;

    @PostMapping("/details")
    public JsonData updateProfileDetails(@RequestBody UpdateProfileDetailsRequest updateRequest) {
        return JsonData.buildSuccess("");
    }

    @PostMapping("/password")
    public JsonData changePassword(@RequestBody ChangePasswordRequest changePassword) {
        return JsonData.buildSuccess("");
    }

    @PostMapping("/delete")
    public JsonData deleteAccount() {
        return JsonData.buildSuccess("");
    }

}
