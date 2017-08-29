package com.fzy.intercepter;

import com.fzy.interceptor.LogInterceptor;
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
public class WebLogInterceptor extends LogInterceptor implements HandlerInterceptor {

	private Logger logger= LoggerFactory.getLogger(WebLogInterceptor.class);


	@Override
	public void saveLog() {
		System.out.println("====测试一下，保存日志======");
	}
}