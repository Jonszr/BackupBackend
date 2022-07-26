package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.UserRepository;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.UserRole;
import ca.sait.backup.service.UserService;
import ca.sait.backup.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository uRepository;

    public boolean validateUser(String email, String password) {

        // Search for user that has the specified email
        User user = this.uRepository.findByEmail(
            email
        );

        if (user == null) {
            return false;
        }

        // Hash what was provided to us
        String hashProvidedPassword = CommonUtils.SHA256(password);

        // Compare both hashes
        if (user.getPassword().equals(hashProvidedPassword)) {
            // Valid
            return true;
        }

        return false;
    }

    @Override
    public User dev_GetUserById(Long id) {

        Optional<User> user = this.uRepository.findById(
            id
        );

        return user.get();
    }

    @Override
    public boolean processRegister(String email, String password, String name, String phone) {

        // Check if email is already used
        if (this.uRepository.findByEmail(email) != null) {
            return false;
        }

        // Hash password
        String hashedPassword = CommonUtils.SHA256(password);

        // Create a new user and save
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setName(name);
        user.setPhone(phone);
        user.setCreationDate(new Date());

        this.uRepository.save(user);

        return true;
    }

    public List<User> dev_GetUsersByRole(UserRole role) {
        List<User> users = this.uRepository.findByRole(
            role
        );

        return users;
    }

    @Override
    public User dev_GetUserByEmail(String email) {
        return this.uRepository.findByEmail(email);
    }

}
