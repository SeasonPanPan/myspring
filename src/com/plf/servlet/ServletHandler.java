/**
 * 
 */
package com.plf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plf.context.ApplicationContext;
import com.plf.utils.CommonUtis;

/**
 * Servlet处理器
 * 
 * @author PLF
 * @date 2019年2月21日
 *
 */
public class ServletHandler {

	private ApplicationContext context;

	/**
	 * @param context
	 */
	public ServletHandler(ApplicationContext context) {
		this.context = context;
	}

	/**
	 * handle
	 *
	 * @param context
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void handle(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String url = req.getRequestURI();
		String contextPath = req.getContextPath();

		url = url.replace(contextPath, "").replaceAll("/+", "/");

		if (context.getHandlerMethod(url) == null) {
			resp.getWriter().write("404 NOT FOUND");
			return;
		}

		Method method = context.getHandlerMethod(url);

		// 获取方法的参数列表
		Class<?>[] parameterTypes = method.getParameterTypes();

		// 获取请求的参数
		Map<String, String[]> parameterMap = req.getParameterMap();

		// 保存参数值
		Object[] paramValues = new Object[parameterTypes.length];

		// 方法的参数列表
		for (int i = 0; i < parameterTypes.length; i++) {
			// 根据参数名称，做某些处理
			String requestParam = parameterTypes[i].getSimpleName();

			if (requestParam.equals("HttpServletRequest")) {
				// 参数类型已明确，这边强转类型
				paramValues[i] = req;
				continue;
			}
			if (requestParam.equals("HttpServletResponse")) {
				paramValues[i] = resp;
				continue;
			}
			if (requestParam.equals("String")) {
				for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
					String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
					paramValues[i] = value;
				}
			}
		}
		
		try {

			// 利用反射机制来调用，第一个参数是method所对应的实例在ioc容器中
			Object result = method.invoke(context.getController(url), paramValues);

			if (result instanceof ModelAndView) {
				handleViewResult((ModelAndView) result, req, resp);
			} else {
				handleObjectResult(result, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 视图处理
	 *
	 * @param view
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private static void handleViewResult(ModelAndView view, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String path = view.getViewName();
		if (CommonUtis.isNotEmpty(path)) {
			
			Map<String, Object> atts = view.getAttributes();
			
			//属性设置到servlet响应
			for (Map.Entry<String, Object> att : atts.entrySet()) {
				req.setAttribute(att.getKey(), att.getValue());
			}

			//页面发送到指定JSP文件
			req.getRequestDispatcher(CommonUtis.JSP_PATH_PREFIX + path).forward(req, resp);
		}
	}

	/**
	 * 直接返回数据
	 *
	 * @param data
	 * @param resp
	 * @throws IOException
	 */
	private static void handleObjectResult(Object data, HttpServletResponse resp) throws IOException {
		if (null != data) {
			// resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();

			// 也可以自己实现JSON数据自动转换
			writer.write(data.toString());
			writer.flush();
			writer.close();
		}
	}

}
