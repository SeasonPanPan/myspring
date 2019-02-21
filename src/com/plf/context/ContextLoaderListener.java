/**
 * 
 */
package com.plf.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Context上下文监听器
 * 
 * @author PLF
 * @date 2019年2月18日
 *
 */
public class ContextLoaderListener implements ServletContextListener{

	private WebApplicationContext context;
	
	public static final String ROOT_CXT_ATTR = "ROOT_CXT";
 
    /**
     * context初始化.
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	System.out.println("contextInitialized");
    	
    	ServletContext servletContext = event.getServletContext();
    	
    	if (null == this.context) {
    		
    		//配置的application文件路径
    		String cfgFile = servletContext.getInitParameter("contextConfigLocation");
    		
			this.context = new WebApplicationContext(cfgFile);
			
			//存入上下文，后面servlet会取用
			servletContext.setAttribute(ROOT_CXT_ATTR, this.context);
		}
    	
    	//源码中是在这里初始化context，调用refresh方法的，我们在上面new的时候先做了
//        initWebApplicationContext(this.context);
    }

	/**
	 * context销毁
	 *
	 * @param arg0
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		System.out.println("contextDestroyed");
		context = null;
	}

	

}
