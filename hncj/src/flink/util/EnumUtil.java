package flink.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

/**
 * ö�ٰ�����
 * 
 * @
 * 
 */
public class EnumUtil {
	/**
	 * ���е�ֵ
	 */
	private Object[] values;

	/**
	 * ���е�����
	 */
	private String[] showNames;

	/**
	 * �������б�
	 */
	private List<LabelValueBean> labelValues = null;

	/**
	 * ����map
	 */
	private Map<Object, String> map = null;

	public EnumUtil(Object[] values, String[] showNames) {
		if (values == null || values.length == 0 || showNames == null || showNames.length == 0) {
			throw new RuntimeException("�����������ֵΪ�գ�");
		}
		if (values.length != showNames.length) {
			throw new RuntimeException("�����ֵ�����ĸ�������ȣ�");
		}
		this.values = values;
		this.showNames = showNames;
	}

	/**
	 * ��ȡ�������б�
	 * 
	 * @return
	 */
	public List<LabelValueBean> getLabelValues() {
		if (this.labelValues == null) {
			this.init();
		}
		return this.labelValues;
	}
	
	/**
	 * ��ȡ��EnumUtil
	 * @param subObjs
	 * @return
	 */
	public EnumUtil getSubEnumUtil(Object[] subObjs){
		if(subObjs == null || subObjs.length ==0){
			return null;
		}
		String[] subShowNames = new String[subObjs.length];
		for(int i=0;i<subObjs.length;i++){
			subShowNames[i] = this.getName(subObjs[i]);
		}
		return new EnumUtil(subObjs,subShowNames);
	}
	

	/**
	 * ��ȡһ��ֵ��Ӧ����ʾ����
	 * 
	 * @param value
	 * @return
	 */
	public String getName(Object value) {
		if (this.map == null) {
			this.init();
		}
		return this.map.get(value);
	}

	/**
	 * ��ȡ�������ֵ�map
	 * 
	 * @return
	 */
	public Map<Object, String> getNameMap() {
		if (this.map == null) {
			this.init();
		}
		return this.map;
	}

	/**
	 * ��ʼ��
	 * 
	 */
	private void init() {
		labelValues = new ArrayList<LabelValueBean>();
		map = new HashMap<Object, String>();
		for (int i = 0; i < values.length; i++) {
			labelValues.add(new LabelValueBean(showNames[i], String.valueOf(values[i])));
			map.put(values[i], showNames[i]);
		}
	}
	
	public static void main(String[] args) {
		EnumUtil test = new EnumUtil(new String[]{"v1","v2"},new String[]{"name1","name2"});
		List<LabelValueBean> list = test.getLabelValues();
		for(LabelValueBean l:list){
			System.out.println("name:"+l.getLabel()+"  value:"+l.getValue());
		}
		
		System.out.println("v1:"+test.getName("v1"));
		System.out.println("v2:"+test.getName("v2"));
	}

}
