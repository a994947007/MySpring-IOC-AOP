import spring.mySpring.core.ClassPathApplicationContext;
import test.UserService;


public class Main {
	public static void main(String[] args) {
		ClassPathApplicationContext context = new ClassPathApplicationContext("spring.xml");
		UserService service = (UserService) context.getBean("userService");
		service.add();
	}
}
