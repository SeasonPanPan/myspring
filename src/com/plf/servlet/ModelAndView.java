/**
 * 
 */
package com.plf.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * ModelAndView.java
 * 
 * @author PLF
 * @date 2019年2月21日
 *
 */
public class ModelAndView {

	/** 视图名，即jsp path */
	private String viewName;

	/** 模型数据 */
	private Map<String, Object> attributes = new HashMap<>();

	public ModelAndView()
	{
		
	}
	
	public ModelAndView(String viewName)
	{
		this.viewName = viewName;
	}
	
	/**
	 * @return the viewName
	 */
	public String getViewName() {
		return viewName;
	}

	/**
	 * @param viewName
	 *            the viewName to set
	 */
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 添加模型数据
	 *
	 * @param attributeName
	 * @param attributeValue
	 */
	public void addObject(String attributeName, Object attributeValue) {
		this.attributes.put(attributeName, attributeValue);
	}
}
