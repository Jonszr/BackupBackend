package ca.sait.backup.service;

import ca.sait.backup.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService{

    boolean validateUser(String email, String password);

    boolean processRegister(String email, String password, String name, String phone);

}
