/**
 * 
 */
package com.plf.context;

/**
 * WebApplicationContext.java
 * 
 * @author PLF
 * @date 2019年2月18日
 *
 */
public class WebApplicationContext extends AbstractApplicationContext {

	/**
	 * 构造方法
	 */
	public WebApplicationContext() {
		
	}
	
	/**
	 * 初始化方法
	 * 
	 * @param cfgFile
	 */
	public WebApplicationContext(String cfgFile) {
		super.cfgFile = cfgFile;
		super.refresh();
		
	}
	

}
