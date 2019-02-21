/**
 * 
 */
package com.plf.utils;

import java.util.Collection;
import java.util.Map;

/**
 * ConfigMap.java
 * 
 * @author PLF
 * @date 2019年2月19日
 *
 */
public final class CommonUtis {

	/**默认的JSP文件路径前缀，实际应该在application xml配置*/
	public static final String JSP_PATH_PREFIX = "/WEB-INF/view/";
	
	/**
	 * string is not empty
	 *
	 * @param src
	 * @return
	 */
	public static boolean isNotEmpty(String src) {
		return src != null && src.trim().length() > 0;
	}
	
	/**
	 * list/set is not empty
	 *
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
	
	/**
	 * map is not empty
	 *
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}
	
	/**
	 * string换为指定type类型
	 *
	 * @param value
	 * @param type
	 * @return
	 */
	public static Object convert(String value, Class<?> type) {

		Object result = null;
		try {

			if (Integer.TYPE.equals(type) || Integer.class.equals(type)) {
				result = Integer.parseInt(value);
			} else if (Long.TYPE.equals(type) || Long.TYPE.equals(type)) {
				result = Long.parseLong(value);
			} else if (Short.TYPE.equals(type) || Short.class.equals(type)) {
				result = Short.parseShort(value);
			} else if (Double.TYPE.equals(type) || Double.class.equals(type)) {
				result = Double.parseDouble(value);
			} else if (Float.TYPE.equals(type) || Float.class.equals(type)) {
				result = Float.parseFloat(value);
			} else if (Boolean.TYPE.equals(type) || Boolean.class.equals(type)) {
				result = Boolean.parseBoolean(value);
			} else {
				//TODO 还有其他类型（比如BigDecimal等）自己实现
				result = type.cast(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
