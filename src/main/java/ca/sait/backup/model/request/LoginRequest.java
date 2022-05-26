package ca.sait.backup.model.request;

import lombok.Data;

/**
 * 登录 request
 */

@Data
public class LoginRequest {

    private String email;

    private String pwd;
}