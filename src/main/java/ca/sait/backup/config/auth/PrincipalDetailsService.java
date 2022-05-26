package ca.sait.backup.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sait.backup.model.entity.googleUser;
import ca.sait.backup.mapper.UserRepository;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		googleUser user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}else {
			return new PrincipalDetails(user);
		}
		
	}

}
