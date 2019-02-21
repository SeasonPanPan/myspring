package com.plf.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean定义类，id-->class
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */
public class BeanDefinition {

	//bean id
	private String id;
	
	//bean对应类全名
	private String className;

	// bean对象的属性
	private List<PropertyDefinition> propertyDefinitions = new ArrayList<PropertyDefinition>();

	/**
	 * 构造函数
	 * 
	 * @param id
	 * @param className
	 */
	public BeanDefinition(String id, String className) {
		this.id = id;
		this.className = className;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the propertyDefinitions
	 */
	public List<PropertyDefinition> getPropertyDefinitions() {
		return propertyDefinitions;
	}

	/**
	 * @param propertyDefinitions
	 *            the propertyDefinitions to set
	 */
	public void setPropertyDefinitions(List<PropertyDefinition> propertyDefinitions) {
		this.propertyDefinitions = propertyDefinitions;
	}
	
	/**
	 * addPropertyDefinition
	 *
	 * @param propertyDefinition
	 */
	public void addPropertyDefinition(PropertyDefinition propertyDefinition) {
		if (null != propertyDefinitions) {
			propertyDefinitions.add(propertyDefinition);
		}
	}

}
