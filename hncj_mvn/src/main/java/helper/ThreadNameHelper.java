package helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程名字帮助类
 *
 */
public class ThreadNameHelper {
	
	/**
	 * 分组线程组的 当前最大的序号
	 */
	public static Map<String,Integer> mapOfMaxNum = new HashMap<String, Integer>();
	
	/**
	 * 设置线程的名字  线程的名字由前缀+_+数字组成
	 * @param thread 线程
	 * @param preOfName  名字的前置。
	 */
	public static synchronized void setThreadName(Thread thread,String preOfName){
		Integer maxNum = mapOfMaxNum.get(preOfName);
		if(maxNum==null){
			maxNum = 0;
		}
		maxNum = maxNum+1;
		mapOfMaxNum.put(preOfName, maxNum);
		
		thread.setName(preOfName+"_"+maxNum);
	}

}
