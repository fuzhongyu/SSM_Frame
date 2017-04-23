package com.fzy.common.config;

import com.fzy.common.utils.PropertiesLoader;
import com.fzy.common.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map =new HashMap<>();

	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("config.properties");
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * @see "${fns:getConfig('adminPath')}"
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value != null ? value : org.apache.commons.lang3.StringUtils.EMPTY);
		}
		return value;
	}


	/**
	 * 获取session超时时间,单位秒
	 */
	public static int getSessionTimeout() {
		String sessionTimeout = getConfig("session.sessionTimeout");
		if (StringUtils.isBlank(sessionTimeout)){
			sessionTimeout = "7200000";//默认2小时
		}
		return Integer.valueOf(sessionTimeout)/1000;
	}

}
