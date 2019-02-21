/**
 * 
 */
package com.plf.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.plf.context.ContextLoaderListener;
import com.plf.context.WebApplicationContext;
import com.plf.utils.CommonUtis;

/**
 * DispatcherServlet继承HttpServlet，重写init方法、doGet、doPost方法
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */
public class DispatcherServlet extends HttpServlet{

	private static final long serialVersionUID = 935673787805906127L;

	private WebApplicationContext context;

	@Override
	public void init(ServletConfig config) throws ServletException {

		Object oldCxt = config.getServletContext().getAttribute(ContextLoaderListener.ROOT_CXT_ATTR);
		this.context = oldCxt == null ? new WebApplicationContext() : (WebApplicationContext)oldCxt;

		ServletContext sc = config.getServletContext();
		
        //注册JSP的Servlet，视图才能重定向
        ServletRegistration jspServlet = sc.getServletRegistration("jsp");
        jspServlet.addMapping(CommonUtis.JSP_PATH_PREFIX + "*");
	}

	/**
	 * doGet
	 *
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 处理请求
		doDispatch(req, resp);
	}
	
	
	/**
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

		try {
			
			ServletHandler servletHandler = new ServletHandler(this.context);
			
			//servlet分发处理器
			servletHandler.handle(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}