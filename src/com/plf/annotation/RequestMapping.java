/**
 * 
 */
package com.plf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RequestMapping 可以在类和方法上
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
	
	/**
	 * 表示访问该方法的url
	 * 
	 * @return
	 */
	String value() default "";

}