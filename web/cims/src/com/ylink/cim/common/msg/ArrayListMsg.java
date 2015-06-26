package com.ylink.cim.common.msg;

import java.util.ArrayList;


public class ArrayListMsg extends ArrayList<Object>
{
	private static final long serialVersionUID = 1L;

	public ArrayListMsg()
	{
		
	}

	public ArrayListMsg(String v_strMsg)
	{
		Parse(v_strMsg);
	}
	
	/**
	 * 解析字符串
	 * @param v_strMsg
	 */
	public void Parse(String v_strMsg)
    {
        this.Parse(v_strMsg, 0, true);
    }
	
	/**
	 * 根据分隔符级别解析字符串
	 * @param v_strMsg     报文字符串
	 * @param v_level      分隔符级别
	 * @param v_bIsFirst   是否是第一层
	 */
    private void Parse(String v_strMsg, int v_level, boolean v_bIsFirst)
    {
        if (isHaveSperatorChar(v_strMsg, v_level) == false)
        {
            //如果字符串是没有分隔符的，则直接以字符串形式添加到集合中
            this.add(v_strMsg);
        }
        else
        {
            //如果字符串没有所包括的级别分隔符，则直接用下级分隔符进行再解析
            if (LastIndexOfByte(v_strMsg, Constant.SEPARATOR_RECORD[v_level]) < 0)
            {
                this.Parse(v_strMsg, v_level + 1, v_bIsFirst);
            }
            else
            {
                ArrayListMsg alSubMsgList = null;
                if (v_bIsFirst)
                {
                    alSubMsgList = this;
                }
                else
                {
                    alSubMsgList = new ArrayListMsg();
                    this.add(alSubMsgList);
                }

                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < v_strMsg.length(); i++)
                {
                    boolean bSepChar = (v_strMsg.substring(i,i+1).equals("" + Constant.SEPARATOR_RECORD[v_level]));
                    boolean bLast = (i == v_strMsg.length() - 1);
                    if (bSepChar || bLast)
                    {
                        if (!bSepChar && bLast)
                            sb.append(v_strMsg.substring(i,i+1));
                        alSubMsgList.Parse(sb.toString(), v_level + 1, false);
                        sb.delete(0, sb.length());
                    }
                    else
                    {
                        sb.append(v_strMsg.substring(i,i+1));
                    }
                }
            }

        }
    }
    
    /**
     * 字符串中是否包含有多级分隔符 
     * @param v_strMsg  字符串
     * @param v_level   分隔符起始级别
     * @return
     */
    private boolean isHaveSperatorChar(String v_strMsg, int v_level)
    {
        for (; v_level < Constant.SEPARATOR_RECORD.length; v_level++)
        {
            for (int i = v_strMsg.length() - 1; i >= 0; i--)
            {
                if ( v_strMsg.substring(i,i+1).equals("" + Constant.SEPARATOR_RECORD[v_level]) )
                    return true;
            }
        }
        return false;
    }
    
    /**
     * 判断字节值B在字符串转换为字节数组后出现的位置
     * @param str  字符串
     * @param b    需要查找的byte值
     * @return
     */
    private int LastIndexOfByte(String str, char b)
    {
        if (str == null || str.length() <= 0)
            return -1;

        for (int i = str.length() - 1; i >= 0; i--)
        {
            if ( str.substring(i,i+1).equals(""+b)) 
                return i;
        }
        return -1;
    }
    
    /**
     * 当集合中的数据转换为字符数组
     * @return
     */
    public String[] toStringArray()
    {
        if (this.size() > 0 && this.get(0) instanceof String )
        {
        	String[] values = new String[this.size()];
            for (int i = 0; i < this.size(); i++)
            {
                values[i] = (String)this.get(i);
            }
            return values;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 生成与黄金二级系统1.0接口规范一致的报文字符串
     */
    public String toString()
    {
    	return this.toString(0);
    }
    
    /**
     * 根据报文分隔符级别生成字符串
     * @param v_level
     * @return
     */
    private String toString(int v_level)
    {
        StringBuffer sbMsgBuff = new StringBuffer();
        if (this.size() > 0)
        {
            StringBuffer sbSubMsgBuff = new StringBuffer();
            if (this.get(0) instanceof String)
            {
            	for ( int i = 0 ; i < this.size() ; i++ )
                {
                    sbSubMsgBuff.append((String)this.get(i));
                    sbSubMsgBuff.append((char)Constant.SEPARATOR_RECORD[v_level]);
                }
                sbMsgBuff.append(sbSubMsgBuff);
            }
            else
            {
            	for ( int i = 0 ; i < this.size() ; i++ )
                {
            		ArrayListMsg alSubMsgList = (ArrayListMsg)this.get(i);
                    sbSubMsgBuff.append( alSubMsgList.toString(v_level + 1));
                    sbSubMsgBuff.append((char)Constant.SEPARATOR_RECORD[v_level]);
                }
                sbMsgBuff.append(sbSubMsgBuff);
            }
        }

        return sbMsgBuff.toString();
    }
    
}