package spring.mySpring.core;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;










import spring.mySpring.core.annotation.After;
import spring.mySpring.core.annotation.Aspect;
import spring.mySpring.core.annotation.Before;
import spring.mySpring.core.annotation.Component;
import spring.mySpring.core.xml.model.ComponentScanConfig;

public class AbstractXMLApplicationContext implements ApplicationContext{
	//所有类
	protected List<Class<?>> classes = new ArrayList<Class<?>>();
	//IOC容器
	protected Map<String,Object> ioc = new HashMap<String,Object>();
	//Before切面容器
	protected List<AspectEntry> beforeAspects = new ArrayList<AspectEntry>();
	//After切面容器
	protected List<AspectEntry> afterAspects = new ArrayList<AspectEntry>();
	
	private XMLScanner scanner = null;
	
	public AbstractXMLApplicationContext(String url) {
		scanner = new XMLScanner(AbstractXMLApplicationContext.class.getClassLoader()
				.getResource(url).getPath());
		initContext();
	}

	/**
	 * 初始化Context
	 */
	protected void initContext(){
		//扫描包，加载所有类
		doScanPackages();
		
		//将加了注解的类，生成对象，注册到IOC容器中
		doInstance();
		
		//对类的属性进行递归注入
		doDI();
		
		//初始化所有切面
		doInitAspect();
	}
	
	/**
	 * 将所有切面加载到集合中
	 */
	private void doInitAspect() {
		for (Entry<String,Object> obj : ioc.entrySet()) {
			if(obj.getValue().getClass().isAnnotationPresent(Aspect.class)){
				for(Method method : obj.getValue().getClass().getMethods()){
					if(method.isAnnotationPresent(Before.class)){
						beforeAspects.add(new AspectEntry(method.getAnnotation(Before.class).value(), method,obj.getValue()));
					}else if(method.isAnnotationPresent(After.class)){
						afterAspects.add(new AspectEntry(method.getAnnotation(After.class).value(), method,obj.getValue()));
					}
				}
			}
		}
		//将所有切面加入到具体的对象中
		for (Entry<String, Object> obj : ioc.entrySet()) {
			Object proxyBean = Proxy.newProxyInstance(obj.getValue().getClass().getClassLoader(), 
					obj.getValue().getClass().getInterfaces(), new JDKInvokationHandler(obj.getValue(),
							beforeAspects,afterAspects));
			ioc.put(obj.getKey(), proxyBean);
			
		}
	}

	/**
	 * 对加了注解的对象进行递归注入
	 */
	private void doDI() {
		for (Entry<String, Object> obj : ioc.entrySet()) {
			for(Field field : obj.getValue().getClass().getDeclaredFields()){
				if(field.isAnnotationPresent(Resource.class)){
					Resource resource = field.getAnnotation(Resource.class);
					String beanName = resource.name();
					field.setAccessible(true);
					try {
						field.set(obj.getValue(), ioc.get(beanName));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 将加了注解的类实例化成对象，注册到IOC容器中
	 */
	private void doInstance() {
		for (Class<?> clazz : classes) {
			if(clazz.isAnnotationPresent(Component.class)){
				try {
					Object obj = clazz.newInstance();
					Component componentAnnotation = clazz.getAnnotation(Component.class);
					String beanName = componentAnnotation.value();
					if(beanName.trim().equals("")){
						beanName = clazz.getName();
						char [] chars = beanName.toCharArray();
						chars[0] += 32;
						beanName = String.valueOf(chars);
					}
					ioc.put(beanName, obj);
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 扫描所有包，将其中的所有类加载到类容器
	 */
	private void doScanPackages(){
		List<ComponentScanConfig> componentScanConfigs = scanner.getComponentScanConfigs();
		for (ComponentScanConfig componentScanConfig : componentScanConfigs) {
			String path = componentScanConfig.getBasePackage();
			scanPackage(path);
		}
	}
	
	/**
	 * 扫描某一个包下的所有包，将其中的类加载到类容器
	 */
	private void scanPackage(String packagePath){
		String path = packagePath.replaceAll("\\.", "/");
		File f = new File(AbstractXMLApplicationContext.class.getClassLoader().getResource(path).getFile());
		File[] files = f.listFiles();
		for (File file : files) {
			if(file.isDirectory()){
				scanPackage(packagePath + "." + file.getName());
			}else if(file.isFile() && file.getName().endsWith(".class")){
				String className = file.getName().substring(0,file.getName().lastIndexOf("."));
				try {
					Class<?> clazz = Class.forName(packagePath + "." + className);
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	@Override
	public Object getBean(String beanName) {
		return ioc.get(beanName);
	}
} 
