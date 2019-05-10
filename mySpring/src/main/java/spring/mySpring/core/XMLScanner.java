package spring.mySpring.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import spring.mySpring.core.xml.model.AspectjAutoproxy;
import spring.mySpring.core.xml.model.ComponentScanConfig;

/**
 * 用来扫描spring.xml配置文件
 * @author 路遥
 *
 */
@SuppressWarnings("unchecked")
public class XMLScanner {
	private File file;
	private List<ComponentScanConfig> componentScanConfigs = new ArrayList<ComponentScanConfig>();
	private List<AspectjAutoproxy> aspectjAutoproxies = new ArrayList<AspectjAutoproxy>();
	
	public XMLScanner(String url){
		this.file = new File(url);
		init();
	}
	
	public void init(){
		scan();
	}
	
	public void scan(){
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(file);
			Element root = document.getRootElement();
			for(Iterator<Element> it = root.elementIterator();it.hasNext();){
				Element element = it.next();
				String elementName = element.getName();
				if(elementName.equals("component-scan")){
					ComponentScanConfig componentScanConfig = new ComponentScanConfig();
					for(Iterator<Attribute> attributes = element.attributeIterator();attributes.hasNext();){
						Attribute attribute = attributes.next();
						String attrName = attribute.getName();
						String attrValue = attribute.getValue();
						if(attrName.equals("base-package")){
							componentScanConfig.setBasePackage(attrValue);
						}else if(attrName.equals("annotation-config")){
							componentScanConfig.setAnnotationConfig(attrValue);
						}else if (attrName.equals("use-default-filters")) {
							componentScanConfig.setUseDefaultFilters(attrValue);
						}else if (attrName.equals("name-generator")) {
							componentScanConfig.setNameGenerator(attrValue);
						}else if (attrName.equals("resource-pattern")) {
							componentScanConfig.setResourcePattern(attrValue);
						}else if (attrName.equals("scope-resolver")) {
							componentScanConfig.setScopeResolver(attrValue);
						} else if(attrName.equals("scoped-proxy")){
							componentScanConfig.setScopedProxy(attrValue);
						}
					}
					componentScanConfigs.add(componentScanConfig);
				}else if(elementName.equals("aspectj-autoproxy")){
					AspectjAutoproxy aspectjAutoproxy = new AspectjAutoproxy();
					for(Iterator<Attribute> attributes = element.attributeIterator();attributes.hasNext();){
						Attribute attribute = attributes.next();
						String attrName = attribute.getName();
						String attrValue = attribute.getValue();
						if(attrName.equals("proxy-target-class")){
							aspectjAutoproxy.setProxyTargetClass(attrValue);
						}
					}
					aspectjAutoproxies.add(aspectjAutoproxy);
				} 
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public List<ComponentScanConfig> getComponentScanConfigs(){
		return this.componentScanConfigs;
	}
	
	public List<AspectjAutoproxy> getAspectjAutoproxies(){
		return this.aspectjAutoproxies;
	}
}
