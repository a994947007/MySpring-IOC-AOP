package spring.mySpring;


import org.junit.Test;

import spring.mySpring.core.ClassPathApplicationContext;
import test.UserService;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
		ClassPathApplicationContext context = new ClassPathApplicationContext("spring.xml");
		UserService service = (UserService) context.getBean("userService");
		service.add();
    	
    }
}
