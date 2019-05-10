package test;

import spring.mySpring.core.annotation.After;
import spring.mySpring.core.annotation.Aspect;
import spring.mySpring.core.annotation.Before;
import spring.mySpring.core.annotation.Component;

@Component
@Aspect
public class LogProxy {

	@Before("test.*..*(..)")
	public void before(){
		System.out.println("before");
	}
	
	@After("test.*..*(..)")
	public void after(){
		System.out.println("after");
	}
}
