package spring.mySpring.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;


public class JDKInvokationHandler implements InvocationHandler{
	private Object obj;
	//Before切面容器
	protected List<AspectEntry> beforeAspects = null;
	//After切面容器
	protected List<AspectEntry> afterAspects = null;
	
	public JDKInvokationHandler(Object obj,List<AspectEntry> beforeAspects,List<AspectEntry> afterAspects) {
		this.obj = obj;
		this.beforeAspects = beforeAspects;
		this.afterAspects = afterAspects;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		for (AspectEntry beforeAspect : beforeAspects) {
			String url = beforeAspect.getUrl();
			url = url.replaceAll("\\*", "\\\\w*");
			url = url.substring(0,url.lastIndexOf("("));
			String methodName = method.toGenericString();
			methodName = methodName.substring(methodName.lastIndexOf(" ") + 1,methodName.lastIndexOf("("));
			if(methodName.matches(url))
				beforeAspect.getMethod().invoke(beforeAspect.getObj());
		}
		method.invoke(obj, args);
		for (AspectEntry afterAspect : afterAspects) {
			String url = afterAspect.getUrl();
			url = url.replaceAll("\\*", "\\\\w*");
			url = url.substring(0,url.lastIndexOf("("));
			String methodName = method.toGenericString();
			methodName = methodName.substring(methodName.lastIndexOf(" ") + 1,methodName.lastIndexOf("("));
			if(methodName.matches(url))
				afterAspect.getMethod().invoke(afterAspect.getObj());
		}
		return proxy;
	}
}
