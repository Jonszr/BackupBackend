package ca.sait.backup.controller;

import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.LoginRequest;
import ca.sait.backup.service.EmailService;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {



    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;



    /**
     * register interface
     * @param userInfo
     * @return
     */
    @PostMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo ) throws MessagingException {

        int rows = userService.create(userInfo);
        emailService.sendHtmlMessage(userInfo.get("email"),"Thank you! Register successfully!","Welcome!");

        return rows == 1 ? JsonData.buildSuccess("Register successfully!"): JsonData.buildError("register failed, please try again");

    }
    @PostMapping("update")
    public JsonData update(@RequestBody Map<String,String> updatedUserInfo) throws MessagingException {
        int rows = userService.update(updatedUserInfo);
        emailService.sendHtmlMessage(updatedUserInfo.get("email"),"Your information updated successfully!","Thank you!");
        return rows == 1 ? JsonData.buildSuccess("update successfully!"): JsonData.buildError("update failed, please try again");
    }


    /**
     * login interface
     * @param loginRequest
     * @return
     */
    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest){

        String token = userService.findByEmailAndPwd(loginRequest.getEmail(), loginRequest.getPwd());

        return token == null ?JsonData.buildError("login failed, please check your password or account right"): JsonData.buildSuccess(token);

    }


    /**
     * find user information by id throng token
     * @param request
     * @return
     */
    @GetMapping("find_by_token")
    public JsonData findUserInfoByToken(HttpServletRequest request){

        Integer userId = (Integer) request.getAttribute("user_id");

        if(userId == null){
            return JsonData.buildError("Query failed");
        }

        User user =  userService.findByUserId(userId);

        return JsonData.buildSuccess(user);

    }








}
