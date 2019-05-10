package test;

import javax.annotation.Resource;

import spring.mySpring.core.annotation.Component;

@Component("userService")
public class UserServiceImpl implements UserService{
	@Resource(name="userDao")
	private UserDao dao;
	
	public void add(){
		dao.save();
	}
}
