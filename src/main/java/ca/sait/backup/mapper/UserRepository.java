package ca.sait.backup.mapper;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.entity.googleUser;

public interface UserRepository extends JpaRepository<googleUser,Long> {
	
	
	googleUser findByUsername(String username);
	Optional<googleUser> findByProviderAndProviderId(String provider, String providerId);
}
