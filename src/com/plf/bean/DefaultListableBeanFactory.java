/**
 * 
 */
package com.plf.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.plf.utils.CommonUtis;

/**
 * DefaultListableBeanFactory.java
 * 
 * @author PLF
 * @date 2019年2月21日7
 *
 */
public class DefaultListableBeanFactory implements BeanFactory{

	// 用来存储所有的beans
	private final List<BeanDefinition> beanDefines = new ArrayList<>();

	// 用来存储实例化后的bean
	private final Map<String, Object> sigletons = new ConcurrentHashMap<>();
	
	/**
	 * getBean
	 *
	 * @param name
	 * @return
	 */
	@Override
	public Object getBean(String name) {
	
		return sigletons.get(name);
	}

	/**
	 * @return the beanDefines
	 */
	public List<BeanDefinition> getBeanDefines() {
		return beanDefines;
	}

	/**
	 * addBeanDefines
	 *
	 * @param beanDefines
	 */
	public void addBeanDefines(List<BeanDefinition> beanDefines) {
		this.beanDefines.addAll(beanDefines);
	}
	
	/**
	 * @return the sigletons
	 */
	public Map<String, Object> getSigletons() {
		return sigletons;
	}

	/**
	 * 实例化bean，但是不包括属性注入
	 *
	 */
	public void instanceBeans() {
		if (CommonUtis.isNotEmpty(beanDefines)) {

			// 对每个bean进行实例化
			for (BeanDefinition beanDefinition : beanDefines) {
				try {
					// bean的class属性存在的时候才进行实例化，否则不进行实例化
					if (CommonUtis.isNotEmpty(beanDefinition.getClassName())) {
						
						// 实例化的关键操作
						sigletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
						System.out.println("bean id为：" + beanDefinition.getId() + "的bean实例化成功");
					}
				} catch (Exception e) {
					System.out.println("bean实例化失败");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 为bean对象的属性注入值
	 * 
	 * Administer 2013-8-18 下午7:59:03
	 */
	public void injectObject() {
		
		// 遍历配置文件中定义的所有的bean
		for (BeanDefinition beanDefinition : beanDefines) {
			
			// 找到要注入的bean
			Object bean = sigletons.get(beanDefinition.getId());
			if (bean != null) {

				try {

					// 通过类Introspector的getBeanInfo方法获取对象的BeanInfo 信息
					BeanInfo info = Introspector.getBeanInfo(bean.getClass());

					// 通过BeanInfo来获取属性的描述器(PropertyDescriptor),通过这个属性描述器就可以获取某个属性对应的getter/setter方法。
					// 然后我们就可以通过反射机制来调用这些方法，获得 bean所有的属性描述
					PropertyDescriptor[] pds = info.getPropertyDescriptors();

					//组装bean的成员变量
					setBeanProperties(beanDefinition, bean, pds);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 设置成员变量
	 *
	 * @param beanDefinition
	 * @param bean
	 * @param pds
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void setBeanProperties(BeanDefinition beanDefinition, Object bean, PropertyDescriptor[] pds)
			throws IllegalAccessException, InvocationTargetException {
		
		// 遍历要注入的bean的所有属性
		for (PropertyDefinition propertyDefinition : beanDefinition.getPropertyDefinitions()) {

			// 遍历要注入bean通过属性描述器得到的所有属性以及行为
			for (PropertyDescriptor propertyDescriptor : pds) {

				// 用户定义的bean属性与spring加载的bean属性名称相同
				if (propertyDefinition.getName().equals(propertyDescriptor.getName())) {

					beanSetInvoke(bean, propertyDefinition, propertyDescriptor);
					
					// 找到了注入的属性后，跳出循环
					break;
				}
			}
		}
	}

	/**
	 * 反射调用，设置字段
	 *
	 * @param bean
	 * @param propertyDefinition
	 * @param propertyDescriptor
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void beanSetInvoke(Object bean, PropertyDefinition propertyDefinition,
			PropertyDescriptor propertyDescriptor) throws IllegalAccessException, InvocationTargetException {
		
		// 获取属性的setter方法
		Method setter = propertyDescriptor.getWriteMethod();

		// 取到了setter方法
		if (setter != null) {

			// 用来存储引用的值
			Object value = null;
			if (CommonUtis.isNotEmpty(propertyDefinition.getRef())) {
				
				// 获取引用的对象的值
				value = sigletons.get(propertyDefinition.getRef());
				
			} else {
				//基本类型赋值
				value=CommonUtis.convert(propertyDefinition.getValue(), propertyDescriptor.getPropertyType());
			}
			
			// 保证setter方法可以访问私有
			setter.setAccessible(true);
			
			// 把引用对象注入到属性
			setter.invoke(bean, value);
		}
	}
	

}
