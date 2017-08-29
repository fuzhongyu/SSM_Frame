package com.fzy.interceptor;

import com.fzy.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 日志拦截器
 */
public abstract class LogInterceptor{

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
		if (logger.isDebugEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）
//	        logger.debug( new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime)+":"+request.getRequestURI()+"  "+request.getParameterMap());

			Map<String,String[]> paramMap=request.getParameterMap();
			StringBuilder params = new StringBuilder("");
			if (paramMap != null){
				for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
					params.append(("".equals(params.toString()) ? "" : ",") + param.getKey() + "=");
					String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
					String paramStr = StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue;
					params.append(paramStr.length()<=30?paramStr:(paramStr.substring(0,27)+"..."));
				}
			}

			logger.debug("开始计时: {}  URI: {}  PARAMETERS:{}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), request.getRequestURL(),params.toString());
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		//可以通过ModelAndView参数来改变显示的视图，或修改发往视图的方法
//		modelAndView.addObject("msg","msg");
//		modelAndView.setViewName("index.jsp");
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

		// 保存日志 ( TO DO )
		saveLog();


		if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
			long endTime = System.currentTimeMillis(); 	//2、结束时间
//	        logger.debug(new SimpleDateFormat("hh:mm:ss.SSS").format(endTime)+ request.getRequestURI());
			logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
					new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024,
					(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
		}
		
	}

	public abstract void saveLog();

}
