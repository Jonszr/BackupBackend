package ca.sait.backup.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;



import ca.sait.backup.model.entity.googleUser;
import lombok.Data;


public class PrincipalDetails implements UserDetails, OAuth2User{

	private static final long serialVersionUID = 1L;
	private googleUser user;
	private Map<String, Object> attributes;

	
	public PrincipalDetails(googleUser user) {
		this.user = user;
	}
	

	public PrincipalDetails(googleUser user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	public googleUser getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getRole();});
		return collet;
	}


	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	
	@Override
	public String getName() {
		return user.getId()+"";
	}
	
}
