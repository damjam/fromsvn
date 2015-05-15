package helper;

import java.util.HashMap;
import java.util.Map;

/**
 * �߳����ְ�����
 * 
 */
public class ThreadNameHelper {

	/**
	 * �����߳���� ��ǰ�������
	 */
	public static Map<String, Integer> mapOfMaxNum = new HashMap<String, Integer>();

	/**
	 * �����̵߳����� �̵߳�������ǰ׺+_+�������
	 * 
	 * @param thread
	 *            �߳�
	 * @param preOfName
	 *            ���ֵ�ǰ�á�
	 */
	public static synchronized void setThreadName(Thread thread, String preOfName) {
		Integer maxNum = mapOfMaxNum.get(preOfName);
		if (maxNum == null) {
			maxNum = 0;
		}
		maxNum = maxNum + 1;
		mapOfMaxNum.put(preOfName, maxNum);

		thread.setName(preOfName + "_" + maxNum);
	}

}
