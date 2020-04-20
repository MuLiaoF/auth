package cn.wandingkeji.auth.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 字符串的处理
 * @fileName StringUtils.java
 * @packageName com.heplat.utils
 * @user zhangqb
 * @date Sep 13, 2018 9:49:11 AM
 */
@Slf4j
@SuppressWarnings("unchecked")
public class StringUtils {

	/**
	 * 获取去掉横线的uuid
	 * @methodName GetUUID
	 * @return
	 * @user zhangqb
	 * @date Sep 13, 2018 9:49:30 AM
	 */
	public static String GetUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 字符串去除下划线和中线
	 * @methodName StrTrimLine
	 * @param str
	 * @return
	 * @user zhangqb
	 * @date Sep 14, 2018 2:15:44 PM
	 */
	public static String StrTrimLine(String str) {
		if (str == null) {
			return "";
		}
		return str.replace("-", "").replace("_", "");
	}

	/**
	 * json串转换成Map
	 * @methodName GetMapFromJson
	 * @param message
	 * @return map
	 * @user zhangqb
	 * @date Sep 13, 2018 4:06:44 PM
	 */
	public static Map<String, Object> GetMapFromJson(String message) {
		Map<String, Object> map = null;
		try {
			Gson gson = new Gson();
			map = gson.fromJson(message, Map.class);
		} catch (JsonSyntaxException ex) {
			map = new HashMap<String, Object>();
			log.error(message);
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;

	}
	public static Map<String, Object> GetMapFromJson1(String message) {
		Map<String, Object> map = null;
		try {
			Gson gson = new Gson();
			map = gson.fromJson(message, Map.class);
			if (map != null) {
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					map.put(entry.getKey(), entry.getValue());
				}
			}
		} catch (JsonSyntaxException ex) {
			map = new HashMap<String, Object>();
			log.error(message);
			ex.printStackTrace();
			log.error(ex.getMessage());
		}
		return map;

	}
	/**
	 * 将字符串转换成list数组
	 * @methodName GetListFromJson
	 * @param message
	 * @return list
	 * @user zhangqb
	 * @date Sep 19, 2018 3:00:24 PM
	 */
	public static List GetListFromJson(String message) {
		List list = null;
		try {
			Gson gson = new Gson();
			list = gson.fromJson(message, List.class);
		} catch (JsonSyntaxException ex) {
			list = new ArrayList();
			log.error(ex.getMessage());
		}
		return list;

	}

	/**
	 * 将对象转换成json
	 * @methodName GetJsonFromMap
	 * @param obj
	 * @return
	 * @user zhangqb
	 * @date Oct 15, 2018 6:17:08 PM
	 */
	public static String GetJsonFromObj(Object obj) {
		String str = null;
		try {
			  Gson gson = new GsonBuilder()
				        .setDateFormat("yyyy-MM-dd HH:mm:ss")
				        .create();
			str = gson.toJson(obj);
		} catch (JsonSyntaxException ex) {
			str = "";
			log.error(ex.getMessage());
		}
		return str;

	}

	/**
	 * 将字符串转换成double类型的数据
	 * @methodName StrToDouble
	 * @param str
	 * @return double
	 * @user zhangqb
	 * @date Sep 20, 2018 3:13:54 PM
	 */
	public static double StrToDouble(String str) {
		if (str == null) {
			return 0;
		}
		try {
			if (str.startsWith("[") && str.endsWith("]")) {
				str = StrSubBeingEnd(str);
			}
			return Double.valueOf(str).doubleValue();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return 0;
		}
	}

	/**
	 *  去掉字符串的开始和结束字符
	 * @methodName StrSubBeingEnd
	 * @param str
	 * @return 
	 * @user zhangqb
	 * @date Sep 21, 2018 9:40:57 AM
	 */
	public static String StrSubBeingEnd(String str) {
		if (str == null) {
			return "";
		}
		if (str.length() > 1) {
			str = str.substring(1, str.length() - 1);
		}
		return str;
	}

	/**
	 * 去除字符串的开始结束中括号
	 * @methodName StrTrimMidBrace
	 * @param str
	 * @return
	 * @user zhangqb
	 * @date Sep 26, 2018 11:16:24 AM
	 */
	public static String StrTrimMidBrace(String str) {
		if (str == null) {
			return "";
		}
		if (str.startsWith("[") && str.endsWith("]")) {
			str = StrSubBeingEnd(str);
		}
		return str;
	}

	/**
	 * 判断字符串是否为空
	 * @methodName IsEmpty
	 * @param str
	 * @return
	 * @user zhangqb
	 * @date Sep 26, 2018 11:29:10 AM
	 */
	public static boolean IsEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转换成int数字
	 * @methodName StrToInt
	 * @param str
	 * @return
	 * @user zhangqb
	 * @date Sep 28, 2018 2:59:59 PM
	 */
	public static int StrToInt(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return 0;
		}
	}

	/**
	 * 将包路径转换成路径
	 * @methodName PointToSlash
	 * @param str
	 * @return
	 * @user zhangqb
	 * @date Jan 21, 2019 3:52:36 PM
	 */
	public static String PointToSlash(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			str = str.replaceAll("\\.", "/");
			if(!str.startsWith("/")) {
				str="/"+str;
			}
		}
		return str;
	}
	/**
	 * 字符串替换
	 * @methodName ReplaceAll
	 * @param str
	 * @param src
	 * @param des
	 * @return
	 * @user zhangqb
	 * @date Feb 28, 2019 4:32:03 PM
	 */
	public static String ReplaceAll(String str,String src,String des) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			str = str.replaceAll("\\"+src, des);
		}
		return str;
	}
	
	public static void  main(String []aa) {
		String aaa=ReplaceAll("afasfafasf{IMG}","{IMG}","uuuuuuuuu");
		System.out.println(aaa);
	}
}
