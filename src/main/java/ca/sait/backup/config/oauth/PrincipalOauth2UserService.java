package ca.sait.backup.config.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import ca.sait.backup.config.oauth.provider.GoogleUserInfo;

import ca.sait.backup.config.oauth.provider.OAuth2UserInfo;
import ca.sait.backup.model.entity.googleUser;
import ca.sait.backup.mapper.UserRepository;

import ca.sait.backup.config.auth.PrincipalDetails;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;

	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회


		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		
		System.out.println("oAuth2User : " + oAuth2User);
	
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청~~");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());	
		} else {
			System.out.println("우리는 구글만 지원해요 ㅎㅎ");
		}

	Optional<googleUser> userOptional = 
				userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
		googleUser user;
		if (userOptional.isPresent()) {
			user = userOptional.get();

			user.setEmail(oAuth2UserInfo.getEmail());
			userRepository.save(user);
		} else {
			
			user = googleUser.builder()
					.username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
					.email(oAuth2UserInfo.getEmail())
					.role("ROLE_USER")
					.provider(oAuth2UserInfo.getProvider())
					.providerId(oAuth2UserInfo.getProviderId())
					.build();
			userRepository.save(user);
		}

		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}
