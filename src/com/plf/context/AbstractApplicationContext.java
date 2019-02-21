/**
 * 
 */
package com.plf.context;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.plf.annotation.Autowired;
import com.plf.annotation.Controller;
import com.plf.annotation.RequestMapping;
import com.plf.bean.ApplicationXmlResult;
import com.plf.bean.BeanDefinition;
import com.plf.bean.DefaultListableBeanFactory;
import com.plf.bean.PropertyDefinition;
import com.plf.utils.CommonUtis;
import com.plf.utils.XmlUtil;

/**
 * ApplicationContext实现类较多，常用如下：
 * ClassPathXmlApplicationContext和WebApplicationContext
 * 
 * @author PLF
 * @date 2019年2月18日
 *
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

	//默认配置文件名称
	protected String cfgFile = "applicationContext.xml";
	
	//设置url和方法的关系
	private Map<String, Method> handlerMapping = new ConcurrentHashMap<>();

	//设置url和beanid关系
	private Map<String, String> controllerMap = new ConcurrentHashMap<>();

	//bean工厂
	private DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
	
	
	/**
	 * 在spring源码中，refresh方法做了以下事情：<br>
	 * 1、刷新上下文；
	 * 2、初始化BeanFactory，获取bean工厂，这里会解析XML文件；
	 * 3、对BeanFactory的各种功能进行填充，如注解；
	 * 4、激活各种BeanFactory处理器；
	 * 5、注册拦截Bean创建的Bean处理器；
	 * 6、初始化上下文中的资源文件；
	 * 7、初始化上下文事件广播器；
	 * 8、给子类扩展初始化其他Bean；
	 * 9、在所有bean中查找listener bean并注册；
	 * 10、实例化所有非惰性加载的bean；
	 * 11、结束：广播事件，这里面对url做了映射。 
	 * 
	 * 我们简化处理重要的步骤：解析XML，注册bean，实例化bean，保存URL映射关系
	 *
	 * @throws Exception
	 */
	@Override
	public void refresh() {
		
		try {
			// 1.读取applicationContext.xml
			ApplicationXmlResult result = XmlUtil.readAppXml(this.cfgFile);
			
			if (null != result) {
				
				List<BeanDefinition> beanDefines = result.getBeanDefines();
				
				// 2. 扫描文件夹下面类
				this.doScanner(result.getComponentScan(), beanDefines);
				
				// 3.保存所有bean
				beanFactory.addBeanDefines(beanDefines);
				
				// 4.注册、实例化bean
				beanFactory.instanceBeans();
				
				// 5.实现对依赖对象的注入功能
				beanFactory.injectObject();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 扫描 指定目录下的文件
	 * 
	 * @param packageName
	 */
	private void doScanner(String packageName, List<BeanDefinition> beanDefines) {
		
		if (CommonUtis.isNotEmpty(packageName)) {
			
			// 把所有的.替换成/
			URL url = AbstractApplicationContext.class.getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
			File dir = new File(url.getFile());
			
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) {
					// 递归读取包
					doScanner(packageName + "." + file.getName(), beanDefines);
				} else {
					String className = packageName + "." + file.getName().replace(".class", "");
					initScanAnnotation(className, beanDefines);
				}
			}
		}
	}
	
	/**
	 * 扫描注解
	 *
	 * @param className
	 */
	private void initScanAnnotation(String className, List<BeanDefinition> beanDefines) {

		
		try {
			
			String beanId = className.substring(className.lastIndexOf('.') + 1);
			Class<?> clazz = Class.forName(className);
			
			//TODO 目前只实现了controller注解，service和其他注解，自己实现
			if (!clazz.isAnnotationPresent(Controller.class)) {
				
				return;
			}

			// 拼url时,是controller头的url拼上方法上的url
			String baseUrl = "";
			if (clazz.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
				baseUrl = annotation.value();
			}
			
			//方法上的URL
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (!method.isAnnotationPresent(RequestMapping.class)) {
					continue;
				}
				
				RequestMapping annotation = method.getAnnotation(RequestMapping.class);
				String url = annotation.value();

				url = (baseUrl + "/" + url).replaceAll("/+", "/");
				handlerMapping.put(url, method);
				controllerMap.put(url, beanId); //保存URL和bean关系
				
				System.out.println("Controller:" + url + "," + method);
			}								
			
			BeanDefinition beanDefinition = new BeanDefinition(beanId, className);
			beanDefines.add(beanDefinition);
			
			//设置属性
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Autowired.class)) {
					//有Autowired注解的说明是引用类型
					PropertyDefinition propertyDefinition = new PropertyDefinition(field.getName(), null, field.getName());
					beanDefinition.addPropertyDefinition(propertyDefinition);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * getHandlerMethod
	 *
	 * @param url
	 * @return
	 */
	@Override
	public Method getHandlerMethod(String url){
		return handlerMapping.get(url);
	}
	
	/**
	 * getController
	 *
	 * @param url
	 * @return
	 */
	@Override
	public Object getController(String url) {
		if (null != this.beanFactory.getSigletons()) {
			return this.beanFactory.getSigletons().get(controllerMap.get(url));
		}
		return null;
	}
	

	/**
	 * getBean
	 *
	 * @param name
	 * @return
	 */
	@Override
	public Object getBean(String name) {
		
		return this.beanFactory.getBean(name);
	}

}