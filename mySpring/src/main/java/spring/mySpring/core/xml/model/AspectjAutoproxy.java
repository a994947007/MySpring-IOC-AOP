package spring.mySpring.core.xml.model;

/**
 * aop:aspectj-autoproxy配置
 * @author 路遥
 *
 */
public class AspectjAutoproxy {
	private String proxyTargetClass;

	public String getProxyTargetClass() {
		return proxyTargetClass;
	}

	public void setProxyTargetClass(String proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}
}
