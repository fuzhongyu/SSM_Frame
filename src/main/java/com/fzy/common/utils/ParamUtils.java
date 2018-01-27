package com.fzy.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数处理类
 *
 * Created by fuzhongyu on 2017/7/21.
 */
public class ParamUtils {

	
	public static Integer IntegerParam(Object object){
		Integer param = null;
		if(object instanceof String)
			try {
				param = Integer.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}

		if(object instanceof Integer)
			param = (Integer)object;
		return param;
	}


	public static String StringParam(Object object){
		String param = null;
		if(object instanceof String)
			param = (String)object;
		if(object!=null){
			param=object.toString();
		}
		return param;
	}

	public static Long LongParam(Object object){
		Long param = null;
		if(object instanceof String)
			try {
				param = Long.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}

		if(object instanceof Integer )
			param = Long.valueOf((Integer)object);
		if(object instanceof Long)
			param = (Long)object;
		return param;
	}

	public static Double DoubleParam(Object object){
		Double param=null;
		if(object instanceof String){
			try {
				param=Double.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}
		}
		if(object instanceof Double){
			param=(Double)object;
		}

		return param;
	}

	public static Float FloatParam(Object object){
		Float param=null;
		if(object instanceof String){
			try {
				param=Float.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}
		}
		if(object instanceof Double){
			param=(Float) object;
		}
		return param;
	}

	public static Boolean BooleanParam(Object object){
		Boolean param=null;
		if(object instanceof String){
			try {
				param=Boolean.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}
		}
		if(object instanceof Boolean){
			param=(Boolean)object;
		}
		return param;
	}

	public static Byte ByteParam(Object object){
		Byte param=null;
		if(object instanceof String){
			try {
				param=Byte.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}
		}
		if(object instanceof Byte){
			param=(Byte)object;
		}
		return param;
	}

	public static Short ShortParam(Object object){
		Short param=null;
		if(object instanceof String){
			try {
				param=Short.valueOf((String)object);
			}catch (NumberFormatException e){
				return null;
			}
		}
		if(object instanceof Short){
			param=(Short) object;
		}
		return param;
	}



	public static List<?> ReturnArrayList(List<?> list){
		if(list == null)
			return new ArrayList<>();
		return list;
	}

	
}
