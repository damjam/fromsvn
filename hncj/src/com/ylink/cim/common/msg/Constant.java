package com.ylink.cim.common.msg;

import com.ylink.cim.common.msg.util.CommUtil;


public final class Constant
{
			
	//-------------------------------------------GSI配置
	/**过程处理成功标志*/
	public final static int OPRATION_SUCCEED = 0;
	/**回报处理成功标志*/
	public final static String GSI_SUCCEED_RSP = "RSP000000";
	/**回报处理成功消息*/
	public final static String GSI_SUCCEED_MSG = "交易成功";
	
	/** 报盘机心跳包Api名称 */
	public final static String GSI_HEART_TEST_API_NAME = "Hello";
	
	/** 报盘机心跳包响应Api名称 */
	public final static String GSI_HEART_TEST_API_NAME_RSP = "HelloRsp";
	
	public final static String GSI_HEART_TEST_API_NAME_RSP2 = "ConnectTestRsp";
	
	/** 报盘机请求应答Api名称 */
	public final static String GSI_ACK_API_NAME = "Ack";
	

    /** 银行的发电成功回执代码 */
    public final static String BAO_BANK_RETURN_FLAG_SUCCEED = "0000";
    
	/** 节点登录成功的响应代码 */
	public final static String NODE_LOGIN_RSP_CODE_SUCCEED = "0000";
	/** 节点登录失败的响应代码 */
	public final static String NODE_LOGIN_RSP_CODE_FAILD   = "1111";
	
    /**自由包字段分隔符*/
	public final static String GSI_FREE_FILED_SEPARATOR = "|";
    /**自由包记录分隔符*/
	public final static String GSI_FREE_RECORD_SEPARATOR = "^";
    
	/** 输出文件型数据的字段分隔符*/
	public final static String RECV_FILE_FIELD_SEPARATOR = "|";
	
    /**单包*/    
	public final static String GSI_CHAIN_SINGLE = "S";
    /**还有后继包*/
	public final static String GSI_CHAIN_CONTINUE = "N";
  	/**最后一包*/
	public final static String GSI_CHAIN_LAST = "L";
    
	/** 报文中字段域之间的分隔符 */
	public final static char MSG_SEPARATOR_FIELD = '#';
	/** 报文单个域中，字段名称与字段值之间的分隔符 */
	public final static char MSG_SEPARATOR_VALUE = '=';
	
	/**连接状态：未连接*/
	public final static int GSI_NOTLINK = 0;
	/**连接状态：已连接*/
	public final static int GSI_LINK = 1;
	
	/**在线状态：在线*/
	public final static int ONLINE = 1;
	/**在线状态：离线*/
	public final static int OFFLINE = 0;
	
	public final static char BOURSE_MSG_OVER_FLAG = 'O';
	public final static char BOURSE_MSG_LAST_FLAG = 'L';
	
	/** 回车换行符 */
	public final static String sEnterLen = new String(new byte[]{13,10});
	
//-------------------------------------------运行环境
	/**系统环境：windows*/
	public final static String SYSTEM_NAME_WINDOWS = "windows";
	/**系统环境：unix*/
	public final static String SYSTEM_NAME_UNIX = "unix";
	
	//-------------------------------------------分隔符配置
    /** 多级记录分隔符  ^ | , */
    public static char[] SEPARATOR_RECORD = new char[] { '∧', '｜', 'ˇ','¨'};
    
    /** 报文长度域的长度 */
    public static int MSG_LEN_LEN = 8;
    
    /**交易处理成功的响应代码*/
    public static String RSP_CODE_SUCCEED = "DT0000";
    
    /**交易处理失败的响应代码*/
    public static String RSP_CODE_FAILD = "DT0001";
  
     /** 通讯操时错误码   */
    public static String NET_TIMEOUT_ERRCODE = "DT9999";
    
    /**报文类型：交易类*/	
	public final static String MSG_TYPE_TRANS = "1";
	/**报文类型：广播类*/
	public final static String MSG_TYPE_BORADCAST = "2";
	
	/**报文标识：请求报文*/
	public final static String MSG_FLAG_REQ = "1";
	/**报文标识：响应报文*/
	public final static String MSG_FLAG_RSP = "2";

    
	/**当前过行环境的字符集*/
	public static String  CFG_CHARSET_NAME = CommUtil.getConfigByString("CHARSET_NAME","GBK");
	
	/**不需要压缩响应报文的最大字节数*/
	public static int CFG_NOT_ZIP_MAX_SIZE = CommUtil.getConfigByInt("CFT_NOT_ZIP_MAX_SIZE", 500);
	
	/**Socket心跳包发送的间隔时间(秒) */
	public static int CFG_SOCKET_TEST_INV_TIME  = CommUtil.getConfigByInt("SOCKET_TEST_INV_TIME", 5);
	
	/**Socket尝试连接的时间间隔（秒） */
	public static int CFG_SOCKET_LINK_INV_TIME =  CommUtil.getConfigByInt("SOCKET_LINK_INV_TIME", 3);

	/**Socket主动断开连接的超时时间（秒） */
	public static int CFG_SOCKET_TIMEOUT_TIME =  CommUtil.getConfigByInt("SOCKET_TIMEOUT_TIME",100);

	
	/**交易服务器IP地址*/
	public static String CFG_SERVER_HOST_IP = CommUtil.getConfigByString("SERVER_HOST_IP","18.18.18.102");	
	/**交易服务器IP地址*/
	public static int CFG_SERVER_HOST_PORT = CommUtil.getConfigByInt("SERVER_HOST_PORT",13000);
	
	
}
