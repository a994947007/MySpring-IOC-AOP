package test;

import spring.mySpring.core.annotation.Component;

@Component("userDao")
public class UserDaoImpl implements UserDao{
	@Override
	public void save() {
		System.out.println("A user saved to DB");
	}
}
