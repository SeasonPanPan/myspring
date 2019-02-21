/**
 * 
 */
package com.plf.bean;

/**
 * Bean工厂
 * 
 * @author PLF
 * @date 2019年2月18日
 *
 */
public interface BeanFactory {
	
	/**
	 * 通过指定名字获取bean
	 *
	 * @param name
	 * @return
	 */
	Object getBean(String name);
	
}
