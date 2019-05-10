package spring.mySpring.core.xml.model;

/**
 * context:component-scan配置
 * @author 路遥
 *
 */
public class ComponentScanConfig {
	private String basePackage;
	private String nameGenerator;
	private String resourcePattern;
	private String scopeResolver;
	private String scopedProxy;
	private String annotationConfig;
	private String useDefaultFilters;
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	public String getNameGenerator() {
		return nameGenerator;
	}
	public void setNameGenerator(String nameGenerator) {
		this.nameGenerator = nameGenerator;
	}
	public String getResourcePattern() {
		return resourcePattern;
	}
	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}
	public String getScopeResolver() {
		return scopeResolver;
	}
	public void setScopeResolver(String scopeResolver) {
		this.scopeResolver = scopeResolver;
	}
	public String getScopedProxy() {
		return scopedProxy;
	}
	public void setScopedProxy(String scopedProxy) {
		this.scopedProxy = scopedProxy;
	}
	public String getAnnotationConfig() {
		return annotationConfig;
	}
	public void setAnnotationConfig(String annotationConfig) {
		this.annotationConfig = annotationConfig;
	}
	public String getUseDefaultFilters() {
		return useDefaultFilters;
	}
	public void setUseDefaultFilters(String useDefaultFilters) {
		this.useDefaultFilters = useDefaultFilters;
	}
}
