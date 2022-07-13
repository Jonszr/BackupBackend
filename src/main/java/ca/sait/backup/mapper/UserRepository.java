package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String emailAddress);
}
