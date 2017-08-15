package com.fzy.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 日志拦截器
 */
public class LogInterceptor implements HandlerInterceptor {

	private Logger logger= LoggerFactory.getLogger(LogInterceptor.class);

	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");

	/**
	 * 有boolean返回值：如果返回false，请求将被终止，如果返回true请求继续运行
	 * @param request
	 * @param response
	 * @param handler 表示被拦截的请求的目标对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
		if (logger.isDebugEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
	        logger.debug( new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime)+":"+request.getRequestURI()+"  "+request.getParameterMap());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		//可以通过ModelAndView参数来改变显示的视图，或修改发往视图的方法
//		modelAndView.addObject("msg","msg");
//		modelAndView.setViewName("index.jsp");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

		// 保存日志 ( TO DO )

		
		// 打印JVM信息。
		if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
			long endTime = System.currentTimeMillis(); 	//2、结束时间
	        logger.debug(new SimpleDateFormat("hh:mm:ss.SSS").format(endTime)+ request.getRequestURI());
		}
		
	}

}
