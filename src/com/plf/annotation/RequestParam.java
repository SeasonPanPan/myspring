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
 * RequestParam注解,只能注解在参数上
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
	
	/**
     * 表示参数的别名，必填
     * @return
     */
    String value();
}
