package com.ylink.cim.common.msg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.ylink.cim.common.msg.util.MsgUtil;

public class HashtableMsg
{
	//���Ԫ�ص�ArrayList
    private ArrayList<Hashtable<String,String>> m_alValue = new ArrayList<Hashtable<String,String>>();
    
    public HashtableMsg()
    {
    	
    }
    
    public HashtableMsg(String v_sMsg)
    {
    	Parse(v_sMsg);
    }
    
	/** 
     * ���ݻƽ����ϵͳ1.0�ӿڹ淶��������Ӧ���ַ���������
     * 1����һ���ָ����ĵ�һ�δ����ֶ������ֶ������ö����ָ����ָ�
     * 2����һ���ָ����ĵڶ��δ���ʵ�ʵ�ֵ��
     * �磺FieldNameA|FieldNameB|FieldNameC|^11,12,13,|21,22,23,|^
     * @param v_sMsg �ַ���
     */
    public void Parse(String v_sMsg)
    {
        ArrayListMsg almValue = new ArrayListMsg();
        almValue.Parse(v_sMsg);

        if ( almValue.size() >= 2)
        {
        	//�ֶ�����
            ArrayListMsg almFieldName = (ArrayListMsg)almValue.get(0);
            //������¼ֵ
            ArrayListMsg almFieldValues = (ArrayListMsg)almValue.get(1);

            if ( almFieldValues.size() > 0 )
            {
            	//ѭ������ÿ����¼
                for (int i = 0; i < almFieldValues.size(); i++)
                {
                	//һ����¼
                    ArrayListMsg almFieldValue = (ArrayListMsg)almFieldValues.get(i);
                    
                    //�ֶ�������Ŀ���ֶ�ʵ��ֵ����Ŀ�Ƿ���� 
                    if (almFieldName.size() == almFieldValue.size())
                    {
                    	Hashtable<String,String> htValue = new Hashtable<String,String>();
                        for (int j = 0; j < almFieldValue.size(); j++)
                        {
                            htValue.put((String)almFieldName.get(j),(String)almFieldValue.get(j));
                        }
                        this.add(htValue);
                    }
                }
            }
        }
    }
    
    /**
     * ��һ��������������Hashtable����
     * @param v_htValue
     * @param v_sOrderInfoMsg �����ʽ����ʽ��"�ֶ���1 ��������[string/number] ����ʽ[asc/desc],�ֶ���1 ��������[string/number] ����ʽ[asc/desc]"
     */
    public void add(Hashtable<String,String> v_htValue,String v_sOrderInfoMsg)
    {
    	String[][] arrOrderInfo = new String[10][3];
    	int index = 0;
		//���������ֶ�
		if ( v_sOrderInfoMsg != null && v_sOrderInfoMsg.length() > 0 )
		{
			String splitA[] = v_sOrderInfoMsg.split(",");
			for ( int i = 0 ; i < splitA.length ; i++ )
			{
				if ( splitA[i].trim().length() > 0 )
				{
					boolean bIsFindName   = false;
					boolean bIsFindType   = false;
					String splitB[] = splitA[i].trim().split(" ");
					for ( int j = 0 ; j < splitB.length ; j++ )
					{
						String tmpValue = splitB[j].trim();
						if ( tmpValue.length() > 0 )
						{
							if ( bIsFindName == false )
							{
								arrOrderInfo[index][0] =  tmpValue;
								bIsFindName     = true;
							}else if ( bIsFindType == false )
							{
								arrOrderInfo[index][1] = tmpValue;
								bIsFindType = true;
							}else
							{
								arrOrderInfo[index][2] = tmpValue;
								break;
							}
						}
					}
					index++;
				}				
			}
		}
		
		//csl 2009.3.29 ʱ���������ֻ֧��һ�������ֶ�
		index = -1;
		if ( arrOrderInfo[0][0] != null && arrOrderInfo[0][0].length() > 0 )
		{
			String sFieldName  = arrOrderInfo[0][0];
			String sFieldType  = arrOrderInfo[0][1];
			String sOrderType  = arrOrderInfo[0][2];
			
			if ( sFieldType == null || sFieldType.length() <= 0 )
				sFieldType = "string";
			if ( sOrderType == null || sOrderType.length() <= 0 )
				sOrderType = "asc";
			
			String sNewValue  = v_htValue.get(sFieldName);
			
			for ( int i = 0 ; i < m_alValue.size() ; i++ )
			{
				int comp = 0;
				String sItemValue = m_alValue.get(i).get(sFieldName);
				
				if ( sFieldType.toLowerCase().equals("number"))
				{
					if ( sItemValue == null || sItemValue.length() <= 0 )
						sItemValue = "0";
					if ( sNewValue == null || sNewValue.length() <= 0 )
						sNewValue = "0";
					comp = ( new BigDecimal(sItemValue).compareTo(new BigDecimal(sNewValue)));
				}else
				{
					if ( sItemValue == null )
						sItemValue = "";
					if ( sNewValue == null)
						sNewValue = "";
					comp = sItemValue.compareTo(sNewValue);
				}
				if ( sOrderType.toLowerCase().equals("desc") == true ) //����
				{
					if ( comp < 0 )
					{
						index = i;
						break;
					}
				}else //����
				{
					if ( comp > 0 )
					{
						index = i;
						break;
					}
				}
				
			}
			
			if ( index >= 0 )
				m_alValue.add(index,v_htValue);
			else
				m_alValue.add(v_htValue);
			
		}else
		{
			m_alValue.add(v_htValue);
		}
		
		
		
    }
    
    /**
     * ���ʹ��Hashtable��ŵ�Ԫ��ֵ 
     * @param v_htValue
     */
    public void add(Hashtable<String,String> v_htValue)
    {
        m_alValue.add(v_htValue);
    }
    
    /**
     *  �����Hashtable
     * @param v_iIndex
     * @return
     */
    public Hashtable<String,String> get(int v_iIndex)
    {
        if (v_iIndex < m_alValue.size() && v_iIndex >= 0)
        {
            return (Hashtable<String,String>)m_alValue.get(v_iIndex);
        }
        else
        {
            return null;
        }

    }
    
    /**
     * ɾ��Ԫ��
     * @param v_iIndex
     */
    public void remove(int v_iIndex)
    {
    	if ( v_iIndex < m_alValue.size() )
    		m_alValue.remove(v_iIndex);
    }
    
    /**
     * ɾ��Ԫ��
     * @param htKey
     */
    public void remove(Hashtable<String,String> htKey)
    {
    	m_alValue.remove(htKey);
    }
    
    
    /**
     * Ԫ�ظ���
     * @return
     */
    public int size()
    {
    	return this.m_alValue.size();
    }
    
    /**
     * ��дtoString()���������ɷ��ϻƽ����ϵͳ1.0�ӿڹ淶�ı����ַ���
     * @return
     */
    public String toString()
    {
        ArrayListMsg alm = new ArrayListMsg();

        if (m_alValue.size() > 0) 
        {
            //�����ֶ���
            ArrayListMsg almFieldName = new ArrayListMsg();
            Hashtable<String,String> htFieldName = m_alValue.get(0);
            
            Enumeration<String> enumKeys =  htFieldName.keys();
            while ( enumKeys.hasMoreElements() ){
            	String key = (String)enumKeys.nextElement();
            	almFieldName.add(key);
            }
            
            //����Ԫ��ֵ
            ArrayListMsg almFieldValues = new ArrayListMsg();
            for ( int i = 0 ; i < this.m_alValue.size() ; i++ )
            {
            	ArrayListMsg alFieldValue = new ArrayListMsg();
            	Hashtable<String,String> ht = this.m_alValue.get(i);
            	for ( int j = 0 ; j < almFieldName.size() ; j++ )
            	{
            		alFieldValue.add(j,ht.get(almFieldName.get(j)));
            	}
            	almFieldValues.add(alFieldValue);
            }
                    
            alm.add(almFieldName);
            alm.add(almFieldValues);
        }
        return alm.toString();
    }
    
    /**
     * תΪ��������
     * @param objClass
     * @return
     */
    public Object[] toObjects(Class<?> objClass)
    {
    	try{
	    	Object[] objs = new Object[this.size()];
	        for (int i = 0; i < this.size(); i++)
	        {
	        	Object obj = objClass.newInstance();
	        	Hashtable<String,String> htValue = this.m_alValue.get(i);
	            
	            Enumeration<String> enumKeys =  htValue.keys();
	            while ( enumKeys.hasMoreElements() ){
	            	String key = (String)enumKeys.nextElement();
	            	MsgUtil.setFieldValue(obj,key,(String)htValue.get(key));
	            }
	        }
	        return objs;
    	}catch(Exception e){
    		
    	}
    	return null;
    }
}