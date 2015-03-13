package com.ylink.cim.common.msg;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import com.ylink.cim.common.msg.util.MsgUtil;

/**
 * 键值对应模式的报文基类
 */
public class MsgBase
{
	/** 类的字段列表缓存集合 */
	private static ConcurrentHashMap<String,Field[]> sFieldMap = new ConcurrentHashMap<String,Field[]>();
	
	/**
	 * 构造函数
	 */
	public MsgBase()
	{
		
	}
	
	/**
	 * <p> 解析报文，格式：Key=Value#Key=Value  </p>
	 * <p> @param vMsg 报文字符串 </p>
	 */
	public void Parse(String sMsg) throws Exception
	{
		Field[] fields = this.getFields(this.getClass());
    	for ( int i = 0 ; i < fields.length ; i++ )
    	{
    		String fieldName = fields[i].getName();
    		String value = MsgUtil.getMsgFieldValue(sMsg,fieldName);
    		if ( value != null && value.length() > 0 )
    		{
    			MsgUtil.setFieldValue(this,fields[i],value);
    		}
    	}
	}
	
	//重载
	public void Parse(StringBuffer sbMsg) throws Exception
	{
		Parse(sbMsg.toString());
	}
	
	/**
	 * 生成符合黄金二级系统1.0接口要求的报文
	 */
	public String toString()
	{
        return toStringBuffer().toString();   
	}
	
	/**
	 * 生成符合黄金二级系统1.0接口要求的报文
	 */
	public String toString(String sFilterFields)
	{
        return toStringBuffer(sFilterFields).toString();   
	}
	/**
	 * 生成符合黄金二级系统1.0接口要求的报文
	 */
	public StringBuffer toStringBuffer()
	{
		return toStringBuffer(null);
	}
	/**
	 * 生成符合黄金二级系统1.0接口要求的报文  
	 * @param sFilterFields 生成时过滤的字段列表，使用","号分隔
	 * modify by csl 2011.5.12 增加过滤字段的功能
	 */
	public StringBuffer toStringBuffer(String sFilterFields)
	{
		StringBuffer sb = new StringBuffer(500);
		sb.append(Constant.MSG_SEPARATOR_FIELD);
        try
        {
        	Field[] fields = this.getFields(this.getClass());
        	for ( int i = 0 ; i < fields.length ; i++ )
        	{
        		String fieldName = fields[i].getName();
        		if ( sFilterFields != null && sFilterFields.indexOf("," + fieldName + ",") != -1 )
        			continue;
        		String fieldValue = MsgUtil.getFieldValue(this,fields[i]);
        		MsgBase.addMsgFieldValue(sb,fieldName,fieldValue);
        	}
        }catch(Exception e){
//        	CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
        }
        return sb; 
	}
	
	/** 通过缓存获取类的字段列表 */
	protected Field[] getFields(Class vClass)
	{
		Field[] fields = sFieldMap.get(vClass.getName());
		if ( fields == null )
		{
			fields = vClass.getFields();
			sFieldMap.put(vClass.getName(), fields);
		}
		return fields;
	}
	
	/**
	 * 追加字符属性
	 */
	public static StringBuffer addMsgFieldValue(StringBuffer sbSrc,String sFieldName,String sFieldValue )
	{
	
		if ( sFieldName != null && sFieldValue.length() > 0 )
		{
			//组属性名
			sbSrc.append(sFieldName );
			sbSrc.append(Constant.MSG_SEPARATOR_VALUE);
	        // 根据类型组各属性的值
			sbSrc.append(sFieldValue);
	        // 分隔符
			sbSrc.append(Constant.MSG_SEPARATOR_FIELD);
		}
		return sbSrc;
	}
}