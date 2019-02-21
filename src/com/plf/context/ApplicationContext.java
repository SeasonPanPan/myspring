/**
 * 
 */
package com.plf.context;

import java.lang.reflect.Method;

import com.plf.bean.BeanFactory;

/**
 * ApplicationContext
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */
public interface ApplicationContext extends BeanFactory {

	/**
	 * 启动核心方法，初始化bean/context都在这里实现。<br>
	 * 注：实际spring的这个方法在ConfigurableApplicationContext中
	 *
	 * @throws Exception
	 */
	void refresh() throws Exception;
	
	/**
	 * getHandlerMethod
	 *
	 * @param url
	 * @return
	 */
	Method getHandlerMethod(String url);

	/**
	 * getController
	 *
	 * @param url
	 * @return
	 */
	Object getController(String url);
	
	
}
