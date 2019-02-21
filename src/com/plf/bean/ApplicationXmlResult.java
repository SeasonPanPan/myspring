/**
 * 
 */
package com.plf.bean;

import java.util.List;

/**
 * ApplicationXmlResult.java
 * 
 * @author PLF
 * @date 2019年2月21日
 *
 */
public class ApplicationXmlResult {
	
	//component-scan节点
	private String componentScan;
	
	//bean节点
	private List<BeanDefinition> beanDefines;

	//import等其他节点自己实现
	
	/**
	 * @return the componentScan
	 */
	public String getComponentScan() {
		return componentScan;
	}

	/**
	 * @param componentScan the componentScan to set
	 */
	public void setComponentScan(String componentScan) {
		this.componentScan = componentScan;
	}

	/**
	 * @return the beanDefines
	 */
	public List<BeanDefinition> getBeanDefines() {
		return beanDefines;
	}

	/**
	 * @param beanDefines the beanDefines to set
	 */
	public void setBeanDefines(List<BeanDefinition> beanDefines) {
		this.beanDefines = beanDefines;
	}
	
	
}
