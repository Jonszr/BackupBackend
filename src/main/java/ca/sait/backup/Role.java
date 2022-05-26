package ca.sait.backup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	GUEST("ROLE_GUEST", "Guest"),
	USER("ROLE_USER", "User"),
	ADMIN("ROLE_ADMIN", "Admin"),
	MANAGER("ROLE_MANAGER", "Manager");
	private final String key;
	private final String title;
}
