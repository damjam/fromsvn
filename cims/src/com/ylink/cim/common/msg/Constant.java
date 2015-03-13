package com.ylink.cim.common.msg;

import com.ylink.cim.common.msg.util.CommUtil;


public final class Constant
{
			
	//-------------------------------------------GSI����
	/**���̴���ɹ���־*/
	public final static int OPRATION_SUCCEED = 0;
	/**�ر�����ɹ���־*/
	public final static String GSI_SUCCEED_RSP = "RSP000000";
	/**�ر�����ɹ���Ϣ*/
	public final static String GSI_SUCCEED_MSG = "���׳ɹ�";
	
	/** ���̻�������Api���� */
	public final static String GSI_HEART_TEST_API_NAME = "Hello";
	
	/** ���̻���������ӦApi���� */
	public final static String GSI_HEART_TEST_API_NAME_RSP = "HelloRsp";
	
	public final static String GSI_HEART_TEST_API_NAME_RSP2 = "ConnectTestRsp";
	
	/** ���̻�����Ӧ��Api���� */
	public final static String GSI_ACK_API_NAME = "Ack";
	

    /** ���еķ���ɹ���ִ���� */
    public final static String BAO_BANK_RETURN_FLAG_SUCCEED = "0000";
    
	/** �ڵ��¼�ɹ�����Ӧ���� */
	public final static String NODE_LOGIN_RSP_CODE_SUCCEED = "0000";
	/** �ڵ��¼ʧ�ܵ���Ӧ���� */
	public final static String NODE_LOGIN_RSP_CODE_FAILD   = "1111";
	
    /**���ɰ��ֶηָ���*/
	public final static String GSI_FREE_FILED_SEPARATOR = "|";
    /**���ɰ���¼�ָ���*/
	public final static String GSI_FREE_RECORD_SEPARATOR = "^";
    
	/** ����ļ������ݵ��ֶηָ���*/
	public final static String RECV_FILE_FIELD_SEPARATOR = "|";
	
    /**����*/    
	public final static String GSI_CHAIN_SINGLE = "S";
    /**���к�̰�*/
	public final static String GSI_CHAIN_CONTINUE = "N";
  	/**���һ��*/
	public final static String GSI_CHAIN_LAST = "L";
    
	/** �������ֶ���֮��ķָ��� */
	public final static char MSG_SEPARATOR_FIELD = '#';
	/** ���ĵ������У��ֶ��������ֶ�ֵ֮��ķָ��� */
	public final static char MSG_SEPARATOR_VALUE = '=';
	
	/**����״̬��δ����*/
	public final static int GSI_NOTLINK = 0;
	/**����״̬��������*/
	public final static int GSI_LINK = 1;
	
	/**����״̬������*/
	public final static int ONLINE = 1;
	/**����״̬������*/
	public final static int OFFLINE = 0;
	
	public final static char BOURSE_MSG_OVER_FLAG = 'O';
	public final static char BOURSE_MSG_LAST_FLAG = 'L';
	
	/** �س����з� */
	public final static String sEnterLen = new String(new byte[]{13,10});
	
//-------------------------------------------���л���
	/**ϵͳ������windows*/
	public final static String SYSTEM_NAME_WINDOWS = "windows";
	/**ϵͳ������unix*/
	public final static String SYSTEM_NAME_UNIX = "unix";
	
	//-------------------------------------------�ָ�������
    /** �༶��¼�ָ���  ^ | , */
    public static char[] SEPARATOR_RECORD = new char[] { '��', '��', '��','��'};
    
    /** ���ĳ�����ĳ��� */
    public static int MSG_LEN_LEN = 8;
    
    /**���״���ɹ�����Ӧ����*/
    public static String RSP_CODE_SUCCEED = "DT0000";
    
    /**���״���ʧ�ܵ���Ӧ����*/
    public static String RSP_CODE_FAILD = "DT0001";
  
     /** ͨѶ��ʱ������   */
    public static String NET_TIMEOUT_ERRCODE = "DT9999";
    
    /**�������ͣ�������*/	
	public final static String MSG_TYPE_TRANS = "1";
	/**�������ͣ��㲥��*/
	public final static String MSG_TYPE_BORADCAST = "2";
	
	/**���ı�ʶ��������*/
	public final static String MSG_FLAG_REQ = "1";
	/**���ı�ʶ����Ӧ����*/
	public final static String MSG_FLAG_RSP = "2";

    
	/**��ǰ���л������ַ���*/
	public static String  CFG_CHARSET_NAME = CommUtil.getConfigByString("CHARSET_NAME","GBK");
	
	/**����Ҫѹ����Ӧ���ĵ�����ֽ���*/
	public static int CFG_NOT_ZIP_MAX_SIZE = CommUtil.getConfigByInt("CFT_NOT_ZIP_MAX_SIZE", 500);
	
	/**Socket���������͵ļ��ʱ��(��) */
	public static int CFG_SOCKET_TEST_INV_TIME  = CommUtil.getConfigByInt("SOCKET_TEST_INV_TIME", 5);
	
	/**Socket�������ӵ�ʱ�������룩 */
	public static int CFG_SOCKET_LINK_INV_TIME =  CommUtil.getConfigByInt("SOCKET_LINK_INV_TIME", 3);

	/**Socket�����Ͽ����ӵĳ�ʱʱ�䣨�룩 */
	public static int CFG_SOCKET_TIMEOUT_TIME =  CommUtil.getConfigByInt("SOCKET_TIMEOUT_TIME",100);

	
	/**���׷�����IP��ַ*/
	public static String CFG_SERVER_HOST_IP = CommUtil.getConfigByString("SERVER_HOST_IP","18.18.18.102");	
	/**���׷�����IP��ַ*/
	public static int CFG_SERVER_HOST_PORT = CommUtil.getConfigByInt("SERVER_HOST_PORT",13000);
	
	
}
