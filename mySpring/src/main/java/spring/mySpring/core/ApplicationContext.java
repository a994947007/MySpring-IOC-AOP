package spring.mySpring.core;

/**
 * 定义整个IOC容器的核心，该接口中，只有一个简单的获取bean的抽象方法
 * @author 路遥
 *
 */
public interface ApplicationContext {
	public Object getBean(String beanName);
}
