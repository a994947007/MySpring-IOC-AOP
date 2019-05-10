package spring.mySpring.core;

import java.lang.reflect.Method;

public class AspectEntry {
	private String url;
	private Method method;
	private Object obj;
	public AspectEntry(String url,Method method,Object obj){
		this.url = url;
		this.method = method;
		this.obj = obj;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
