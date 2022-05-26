//package ca.sait.backup.test;
//
//import java.util.Optional;
//import java.util.function.Supplier;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import ca.sait.backup.model.entity.googleUser;
//import ca.sait.backup.mapper.UserRepository;
//
//@RestController
//public class OptionalControllerTest {
//
//	@Autowired
//	private UserRepository userRepository;
//	
//	@GetMapping("/test/user/{id}")
//	public googleUser findUser(@PathVariable int id) {
//
//		googleUser user = userRepository.findById(id)
//				.orElseThrow(()-> {
//
//						return new NullPointerException("없어 값");
//					
//					
//				});
//		
//		return user;
//	}
//}
