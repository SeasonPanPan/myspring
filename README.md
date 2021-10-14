
最近详细阅读了spring的框架，从简单的配置到注解，从加载到运行，从返回简单string到返回视图，看的很慢。有不理解的地方就一次又一次debug流程，梳理运行过程和调用的方法。为了加深当前对spring的理解，也防止以后遗忘，又花了几天时间把写了一个迷你版的spring框架。迷你spring已经实现了注解和视图，支持tomcat运行和main方法直接调用。代码都上传到了github，请大家下载点星，感谢！！

github地址：https://github.com/SeasonPanPan/myspring

先看以下spring源码中的类关系图(CSDN原地址查看)：

我认为spring源码中最核心的类是AbstractApplicationContext，这个类中的refresh方法做的11大步骤，我已经在myspring中注释了。

	/**
	 * 在spring源码中，refresh方法做了以下事情：<br>
	 *
	 * 1、刷新上下文；
	 * 2、初始化BeanFactory，获取bean工厂，这里会解析XML文件；
	 * 3、对BeanFactory的各种功能进行填充，如注解；
	 * 4、激活各种BeanFactory处理器；
	 * 5、注册拦截Bean创建的Bean处理器；
	 * 6、初始化上下文中的资源文件；
	 * 7、初始化上下文事件广播器；
	 * 8、给子类扩展初始化其他Bean；
	 * 9、在所有bean中查找listener bean并注册；
	 * 10、实例化所有非惰性加载的bean；
	 * 11、结束：广播事件，这里面对url做了映射。 
	 * 
	 * 我们简化处理重要的步骤：解析XML，注册bean，实例化bean，保存URL映射关系
	 *
	 * @throws Exception
	 */
	@Override
	public void refresh() {
        ... //省略代码
    }
	
myspring代码中也尽量使用了源码的重要类名的定义，可以帮助理解源码。

主要的类涉及AbstractApplicationContext、BeanDefinition、DefaultListableBeanFactory、ContextLoaderListener、DispatcherServlet和ServletHandler等。

最后我写了测试类分别测试了在tomcat中启动，浏览器访问myspring项目情况，下图是启动图。
(CSDN原地址查看)


下图是在main方法中测试图
(CSDN原地址查看)


以上是我手写spring框架情况，欢迎大家指正。
--------------------- 
作者：kuailebuzhidao 
来源：CSDN 
原文：https://blog.csdn.net/kuailebuzhidao/article/details/87869279 
版权声明：本文为博主原创文章，转载请附上博文链接！

*欢迎大家关注公众号，有资料有技术文章*

![欢迎大家关注公众号，有技术文章有资料](http://img.zqzhaopin.cn/javaqr.jpg)

