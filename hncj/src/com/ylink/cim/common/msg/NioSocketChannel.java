package com.ylink.cim.common.msg;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.ylink.cim.common.msg.util.CommUtil;
import com.ylink.cim.common.msg.util.DESede;
import com.ylink.cim.common.msg.util.MsgUtil;

public class NioSocketChannel {

	/** ����ͷ���� */
	private int mHeadLen = 0;

	private Socket m_Socket = null;

	DataOutputStream m_outputStream = null;

	DataInputStream m_inputStream = null;

	private int m_TimeOut = 0;

	/** �Ƿ���������Զ����� ѹ�� ���� */
	private boolean bIsAutoUnZipByReqMsg = false;

	/** �Ƿ���������Զ����� ���� ���� add by csl 2010.8.10 */
	private boolean bIsAutoDecryptByReqMsg = true;

	/** ����ģʽ��Ĭ�ϲ����� */
	private byte mEncryptMode = SSLConstant.ENCRYPT_MODE_NO;

	/** �ỰID */
	private String mSessionId = "";

	public NioSocketChannel(int vTimeOut) {
		this.m_TimeOut = vTimeOut;
		mHeadLen = Constant.MSG_LEN_LEN;
	}

	/**
	 * ���ü�����Ϣ
	 * 
	 * @param iEncryptMode
	 *            ����ģʽ
	 * @param sSessionId
	 *            �ỰID
	 */
	public void setEncryptInfo(byte iEncryptMode, String sSessionId) {
		this.mEncryptMode = iEncryptMode;
		this.mSessionId = sSessionId;
	}

	/**
	 * �ر�����
	 */
	public void closeSocket() {
		if (this.m_inputStream != null)
			try {
				m_inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		m_inputStream = null;

		if (this.m_outputStream != null)
			try {
				m_outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		m_outputStream = null;

		if (m_Socket != null)
			try {
				m_Socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		m_Socket = null;
	}

	/***
	 * 
	 * @return
	 */
	public boolean getIsConnected() {
		if (m_Socket == null)
			return false;
		else
			return m_Socket.isConnected();
	}

	/***
	 * �������ӷ�����
	 * 
	 * @param v_sHost
	 * @param v_iPort
	 * @return
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public boolean disConnect(String v_sHost, int v_iPort) {
		try {
			this.closeSocket();
			m_Socket = new Socket();
			// �ͻ��˸���IP�Ͷ˿ں�
			SocketAddress socketAddress = new InetSocketAddress(v_sHost,
					v_iPort);
			if (m_TimeOut > 0)
				m_Socket.connect(socketAddress, m_TimeOut);
			else
				m_Socket.connect(socketAddress);

			m_inputStream = new DataInputStream(this.m_Socket.getInputStream());
			m_outputStream = new DataOutputStream(
					this.m_Socket.getOutputStream());

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * 
	 * @param v_sMsg
	 * @throws Exception
	 */
	public void SendStringMsg(String v_sMsg) throws Exception {
		if (v_sMsg == null || v_sMsg.length() <= 0 || m_Socket == null)
			return;
		v_sMsg = CommUtil.FILL(String.valueOf(v_sMsg.toString().length()), '0',
				Constant.MSG_LEN_LEN, 'L') + v_sMsg.toString();
		// v_sMsg =
		// CommUtil.FillHeadLength(v_sMsg.toString(),'0',Constant.MSG_LEN_LEN,
		// 'L');
		StringBuffer v_bufMsg = new StringBuffer(v_sMsg);
		System.out.println(v_bufMsg.toString());
		this.SendBufferMsg(v_bufMsg);
	}

	/**
	 * ��Ӵ����͵ı��ģ�����ͨ�Ų��ֵ�6λ����
	 * 
	 * @throws Exception
	 */
	public void SendBufferMsg(StringBuffer v_sMsg) throws Exception {
		ByteBuffer msgBuff = ByteBuffer.wrap(MsgUtil.StringToBytes(v_sMsg
				.toString()));
		System.out.println(new String(msgBuff.toString()));
		doWriteBufferMsg(msgBuff);
	}

	/**
	 * ��Ӵ����͵ı��ģ�����ͨ�Ų��ֵ�6λ����
	 * 
	 * @throws Exception
	 */
	public synchronized void doWriteBufferMsg(ByteBuffer msgBuff)
			throws Exception {
		ByteBuffer writeBuff = msgBuff;

		// �ȼ��ܺ�ѹ��
		ByteBuffer zipBuff = null;
		OutputStream os;
		if (this.bIsAutoUnZipByReqMsg) {
			// ����ѹ�������
			if (this.mEncryptMode == SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP) {
				byte[] zipBytes = this.zipWriteBytes(writeBuff.array());
				zipBuff = ByteBuffer.wrap(DESede.encryptSrcMsg(this.mHeadLen,
						this.mEncryptMode, this.mSessionId, zipBytes));
			} else {
				byte[] zipBytes = this.zipWriteBytes(writeBuff.array());
				zipBuff = ByteBuffer.wrap(zipBytes);
			}
		} else {
			zipBuff = writeBuff;
		}
		zipBuff.position(0);
		os = this.m_Socket.getOutputStream();
		os.write(zipBuff.array());
		os.flush();
	}

	/***
	 * ������ʽ���շ��ϻƽ��׶���ϵͳ�ӿڹ淶�ı���
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized String doReadBufferMsg() throws Exception {
		if (m_Socket == null)
			return "";

		byte[] bHead = RecvByLen(Constant.MSG_LEN_LEN);

		int len = Integer.parseInt(new String(bHead));

		byte[] bMsgs = RecvByLen(len);
		byte[] unzipByte = null;
		byte[] descryptByte = null;

		if (this.bIsAutoUnZipByReqMsg)
			unzipByte = this.unzipReadBytes(bMsgs);
		else
			unzipByte = bMsgs;

		byte stmpEncryptMode = unzipByte[len - 1];
		if (stmpEncryptMode != SSLConstant.ENCRYPT_MODE_DEFAULT
				&& stmpEncryptMode != SSLConstant.ENCRYPT_MODE_SESSION
				&& stmpEncryptMode != SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP) // �Ự��Կ
		{
			stmpEncryptMode = SSLConstant.ENCRYPT_MODE_NO;
		}

		// �жϼ���ģʽ�ǲ�������� �����ǲ������ֱ�ӹر�Socket add by csl 2010.11.1
		if ((stmpEncryptMode == SSLConstant.ENCRYPT_MODE_NO
				|| stmpEncryptMode == SSLConstant.ENCRYPT_MODE_DEFAULT
				|| stmpEncryptMode == SSLConstant.ENCRYPT_MODE_SESSION || stmpEncryptMode == SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP)) {
			descryptByte = unzipByte;
			this.closeSocket();
		} else {
			if (stmpEncryptMode == SSLConstant.ENCRYPT_MODE_NO
					|| this.bIsAutoDecryptByReqMsg == false) {
				descryptByte = unzipByte;
			} else {
				descryptByte = DESede.decryptSrcMsg(this.mHeadLen, unzipByte);
				if (stmpEncryptMode == SSLConstant.ENCRYPT_MODE_DEFAULT_ZIP) {
					descryptByte = this.unzipReadBytes(descryptByte);
				}
			}
		}

		return MsgUtil.ByteToString(descryptByte);
	}

	/***
	 * ���չ̶����ȵ�����
	 * 
	 * @param v_iRecvLen
	 * @return
	 */
	private byte[] RecvByLen(int v_iRecvLen) throws Exception {
		byte[] recvBuff = new byte[v_iRecvLen];
		int iRecvIndex = 0;

		while (iRecvIndex < v_iRecvLen) {
			int iCanReadLen = v_iRecvLen - iRecvIndex;
			if (iCanReadLen > 1024)
				iCanReadLen = 1024;

			byte[] tempBuff = new byte[iCanReadLen];

			try {

				int iLen = m_inputStream.read(tempBuff);

				if (iLen > 0) {
					for (int i = 0; i < iLen; i++) {
						recvBuff[iRecvIndex + i] = tempBuff[i];
					}
					iRecvIndex += iLen;
				} else if (iLen == 0) {
					throw new Exception("���ӱ�Զ������ǿ�ƹرգ�");
				} else {
					Thread.sleep(10);
				}
			} catch (Exception e) {
				throw new Exception("���շ�����Ӧ��Ϣ��ʱ.[" + e.toString() + "]");
			}

		}
		return recvBuff;
	}

	/**
	 * ѹ���������
	 * 
	 * @param vWriteBytes
	 *            ԭʼ�������������
	 * @return
	 */
	private byte[] zipWriteBytes(byte[] vWriteBytes) {
		try {
			if (this.bIsAutoUnZipByReqMsg == true
					&& vWriteBytes.length > Constant.CFG_NOT_ZIP_MAX_SIZE) {
				if (vWriteBytes[this.mHeadLen] == SSLConstant.ENCRYPT_MODE_DEFAULT
						|| vWriteBytes[this.mHeadLen] == SSLConstant.ENCRYPT_MODE_SESSION)
					return vWriteBytes;

				byte[] zipBuff = CommUtil.zip(vWriteBytes);
				byte[] outBuff = new byte[zipBuff.length + this.mHeadLen + 1];
				byte[] lenBuff = CommUtil.FILL("" + (zipBuff.length + 1), '0',
						this.mHeadLen, 'L').getBytes();

				outBuff[this.mHeadLen] = 0x01;
				System.arraycopy(lenBuff, 0, outBuff, 0, this.mHeadLen);
				System.arraycopy(zipBuff, 0, outBuff, this.mHeadLen + 1,
						zipBuff.length);

				System.out.println("�����������ѹ��[ԭ����=" + vWriteBytes.length
						+ ",ѹ���󳤶�=" + outBuff.length + "]");

				return outBuff;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vWriteBytes;
	}

	/**
	 * ��ѹ����ȡ����
	 * 
	 * @param vReadBytes
	 *            ���յ������������ֽ�����
	 * @return
	 */
	private byte[] unzipReadBytes(byte[] vReadBytes) {
		try {
			if (vReadBytes.length > this.mHeadLen
					&& vReadBytes[this.mHeadLen] == 0x1) {
				byte[] unzipBuff = new byte[vReadBytes.length - this.mHeadLen
						- 1];
				System.arraycopy(vReadBytes, this.mHeadLen + 1, unzipBuff, 0,
						unzipBuff.length);
				return CommUtil.unzip(unzipBuff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vReadBytes;
	}
}
