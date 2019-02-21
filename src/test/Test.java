/**
 * 
 */
package test;

import com.plf.context.ApplicationContext;
import com.plf.context.ClassPathXmlApplicationContext;

import test.service.UserService;

/**
 * @author PLF
 *
 */
public class Test {

	/**
	 * main
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		 UserService userService = (UserService) ctx.getBean("userService");
		 System.out.println(userService.getUser(123));
		 System.out.println(userService.getUser(2));
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
				//还有其他类型BigDecimal等自己实现
				result = type.cast(value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
