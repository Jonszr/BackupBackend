package ca.sait.backup.service;

import ca.sait.backup.model.entity.User;

import java.util.Map;

public interface UserService {

    /**
     * new user
     * @param userInfo
     * @return
     */
    int create(Map<String, String> userInfo);

    String findByEmailAndPwd(String email, String pwd);

    User findByUserId(Integer userId);
}
