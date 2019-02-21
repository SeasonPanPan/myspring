/**
 * 
 */
package com.plf.context;

/**
 * ClassPathXmlApplicationContext.java
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	
	/**
	 * 构造方函数
	 * 
	 * @param fileName
	 */
	public ClassPathXmlApplicationContext(String fileName) {

		super.cfgFile = fileName;
		super.refresh();
	}

	
	
	
}
