package ca.sait.backup.controller;

import ca.sait.backup.exception.CustomExceptionHandler;

import ca.sait.backup.model.request.LoginRequest;
import ca.sait.backup.model.request.RegisterRequest;
import ca.sait.backup.model.response.LoginResponse;
import ca.sait.backup.model.response.RegisterResponse;
import ca.sait.backup.service.EmailService;

import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired UserService userService;


    @PostMapping("/login")
    private LoginResponse processLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse lResponse = new LoginResponse();

        System.out.println("Email: " + loginRequest.getEmail());
        System.out.println("Password: " + loginRequest.getPassword());

        lResponse.setAuthenticated(true);
        return lResponse;
    }

    @PostMapping("/register")
    private RegisterResponse processRegister(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse rResponse = new RegisterResponse();

        boolean created = this.userService.processRegister(
            registerRequest.getEmail(),
            registerRequest.getPassword(),
            registerRequest.getName(),
            registerRequest.getPhone()
        );

        rResponse.setCreationStatus(created);

        // TODO: Send authenticated JWT token.

        return rResponse;
    }


}
