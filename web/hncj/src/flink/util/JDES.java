package flink.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;



/**
 * <p>Title:DES���ܹ����ࡣ֧�ֶ��ļ��ļ��� </p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ��j</p>
 * @author �����
 * @version 1.0
 */
public class JDES {

	private byte[] m_desKey;
	private static JDES instance;
	/**
	 * @param args
	 */
	public static JDES getInstanse() {
		if (instance == null)
		{
			instance = new JDES();
		}
		return instance;
	}	
	public static void main(String[] args) 
	{
		// 
		try
		{
			JDES des = new JDES();
			des.SetKey("12345678".getBytes());
			/*
			byte[] buf=new byte[1024];
			String value="12345678adfadsfasdfadsfadsfa";
			
			System.arraycopy(value.getBytes(), 0, buf, 0, value.length()) ;
			byte[] encryptText  = des.doECBEncrypt(buf,value.length());
			System.out.println("doDecrypt - " + toHexString(encryptText));
			System.out.println("doDecrypt - " + new String(encryptText,0,encryptText.length));
			
			byte[] decryptText = des.doECBDecrypt(encryptText,encryptText.length);
			System.out.println("doDecrypt - " + toHexString(decryptText));
			System.out.println("doDecrypt - " + new String(decryptText,0,decryptText.length));
			*/
			
			//des.doECBEncrypt_File("d:\\myfile.txt","d:\\myfile.txt.des");
			//des.doECBDecrypt_File("d:\\myfile.txt.des","d:\\myfile1.txt");
			des.doECBEncrypt_File("d:\\ҩ����һ�1�.xls","d:\\ҩ����һ�1�.xls.des");
			des.doECBDecrypt_File("d:\\ҩ����һ�1�.xls.des","d:\\ҩ����һ�1�1.xls");
			
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
    public void SetKey(byte[] desKey) 
    {
        this.m_desKey = desKey;
    }
	public void  doECBEncrypt_File(String inFile,String outFile) throws Exception
	{
		//DES�㷨Ҫ����һ������ε������Դ
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = m_desKey;/* ��ĳ�ַ�������ܳ���� */
		// ��ԭʼ�ܳ���ݴ���DESKeySpec����
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		// ����һ���ܳ׹�����Ȼ�������DESKeySpecת����
		// һ��SecretKey����
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher����ʵ����ɼ��ܲ���
	    //Cipher cipher = Cipher.getInstance("DES");
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    // ���ܳ׳�ʼ��Cipher����
	    cipher.init(Cipher.ENCRYPT_MODE, key, sr);
	    // ���ڣ���ȡ��ݲ�����
		
		//�����ļ���Ϊ�ļ������� 
		FileInputStream fin=new FileInputStream(inFile); 
		//��b�ļ������ 
		FileOutputStream fout=new FileOutputStream(outFile); 
 
		byte[] buf=new byte[1024]; 
		int nStart,num; 
		byte[] decryptText; 
		nStart = 0;
		while ((num=fin.read(buf,0,buf.length)) != -1) 
		{ 
			decryptText = cipher.update(buf,0 , num);
			fout.write(decryptText); 
			nStart += num;
		} 
		decryptText = cipher.doFinal();
		// ��ʽִ�м��ܲ���
		fout.write(decryptText); 
		fout.close(); 
		fin.close(); 
			
		
	}
	public void doECBDecrypt_File(String inFile,String outFile) throws Exception
	{
        //DES�㷨Ҫ����һ������ε������Դ
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = m_desKey; /* ��ĳ�ַ�����ȡԭʼ�ܳ���� */
        // ��ԭʼ�ܳ���ݴ���һ��DESKeySpec����
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // ����һ���ܳ׹�����Ȼ�������DESKeySpec����ת����
        // һ��SecretKey����
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        // Cipher����ʵ����ɽ��ܲ���
        //Cipher cipher = Cipher.getInstance("DES");
        // using DES in ECB mode
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       
        // ���ܳ׳�ʼ��Cipher����
        cipher.init(Cipher.DECRYPT_MODE, key, sr);

	       
		//�����ļ���Ϊ�ļ������� 
		FileInputStream fin=new FileInputStream(inFile); 
		//��b�ļ������ 
		FileOutputStream fout=new FileOutputStream(outFile); 
 
		byte[] buf=new byte[1024]; 
		int nStart,num; 
		byte[] decryptText; 
		nStart = 0;
		while ((num=fin.read(buf,0,buf.length)) != -1) 
		{ 
			decryptText = cipher.update(buf,0 , num);
			fout.write(decryptText); 
			nStart += num;
			//decryptText = doECBDecrypt(buf);				
		} 
		decryptText = cipher.doFinal();
	    // ��ʽִ�м��ܲ���
		fout.write(decryptText); 
		fout.close(); 
		fin.close(); 
			
	}
    /**
    *
    * @return DES�㷨��Կ
    */
   public static byte[] generateKey() {
       try {

           // DES�㷨Ҫ����һ������ε������Դ
           SecureRandom sr = new SecureRandom();

           // ���һ��DES�㷨��KeyGenerator����
           KeyGenerator kg = KeyGenerator.getInstance("DES");
           kg.init(sr);

           // �����Կ
           SecretKey secretKey = kg.generateKey();

           // ��ȡ��Կ���
           byte[] key = secretKey.getEncoded();

           return key;
       } catch (NoSuchAlgorithmException e) {
           System.err.println("DES�㷨�������Կ���!");
           e.printStackTrace();
       }

       return null;
   }
   //һ��Ҫ���볤��
   public byte[] doECBEncrypt(byte[] plainText,int len) throws Exception 
   {
       //DES�㷨Ҫ����һ������ε������Դ
       SecureRandom sr = new SecureRandom();
       byte rawKeyData[] = m_desKey;/* ��ĳ�ַ�������ܳ���� */
       // ��ԭʼ�ܳ���ݴ���DESKeySpec����
       DESKeySpec dks = new DESKeySpec(rawKeyData);
       // ����һ���ܳ׹�����Ȼ�������DESKeySpecת����
       // һ��SecretKey����
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
       SecretKey key = keyFactory.generateSecret(dks);
       // Cipher����ʵ����ɼ��ܲ���
       //Cipher cipher = Cipher.getInstance("DES");
       Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       // ���ܳ׳�ʼ��Cipher����
       cipher.init(Cipher.ENCRYPT_MODE, key, sr);
       // ���ڣ���ȡ��ݲ�����
       byte data[] = plainText;/* ��ĳ�ַ�����ȡ��� */
       // ��ʽִ�м��ܲ���
       //cipher.update(data,0,len);
       //byte encryptedData[] = cipher.doFinal();
       byte encryptedData[] = cipher.doFinal(data,0,len);
       
       return encryptedData;
   }
   public byte[] doECBDecrypt(byte[] encryptText,int len) throws Exception 
   {
       //DES�㷨Ҫ����һ������ε������Դ
       SecureRandom sr = new SecureRandom();
       byte rawKeyData[] = m_desKey; /* ��ĳ�ַ�����ȡԭʼ�ܳ���� */
       // ��ԭʼ�ܳ���ݴ���һ��DESKeySpec����
       DESKeySpec dks = new DESKeySpec(rawKeyData);
       // ����һ���ܳ׹�����Ȼ�������DESKeySpec����ת����
       // һ��SecretKey����
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
       SecretKey key = keyFactory.generateSecret(dks);
       // Cipher����ʵ����ɽ��ܲ���
       //Cipher cipher = Cipher.getInstance("DES");
       // using DES in ECB mode
       Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       
       // ���ܳ׳�ʼ��Cipher����
       cipher.init(Cipher.DECRYPT_MODE, key, sr);
       // ���ڣ���ȡ��ݲ�����
       byte encryptedData[] = encryptText;/* ��þ�����ܵ���� */
       // ��ʽִ�н��ܲ���
       //cipher.update(encryptedData,0,len);
       //byte decryptedData[] = cipher.doFinal();
       byte decryptedData[] = cipher.doFinal(encryptedData,0,len);
       return decryptedData;
   }

   /**
    * ���ܺ���
    *
    * @param data
    *            �������
    * @param key
    *            ��Կ
    * @return ���ؼ��ܺ�����
    */
   public byte[] CBCEncrypt(byte[] data, byte[] iv) {

       try {
           // ��ԭʼ��Կ��ݴ���DESKeySpec����
           DESKeySpec dks = new DESKeySpec(m_desKey);

           // ����һ���ܳ׹�����Ȼ�������DESKeySpecת����
           // һ��SecretKey����
           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           SecretKey secretKey = keyFactory.generateSecret(dks);

           // Cipher����ʵ����ɼ��ܲ���
           Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
           // �����NoPaddingģʽ��data���ȱ�����8�ı���
           // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

           // ���ܳ׳�ʼ��Cipher����
           IvParameterSpec param = new IvParameterSpec(iv);
           cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

           // ִ�м��ܲ���
           byte encryptedData[] = cipher.doFinal(data);

           return encryptedData;
       } catch (Exception e) {
           System.err.println("DES�㷨��������ݳ��!");
           e.printStackTrace();
       }

       return null;
   }

   /**
    * ���ܺ���
    *
    * @param data
    *            �������
    * @param key
    *            ��Կ
    * @return ���ؽ��ܺ�����
    */
   public byte[] CBCDecrypt(byte[] data,byte[] iv) {
       try {
           // ��ԭʼ�ܳ���ݴ���һ��DESKeySpec����
           DESKeySpec dks = new DESKeySpec(m_desKey);

           // ����һ���ܳ׹�����Ȼ�������DESKeySpec����ת����
           // һ��SecretKey����
           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           SecretKey secretKey = keyFactory.generateSecret(dks);

           // using DES in CBC mode
           Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
           // �����NoPaddingģʽ��data���ȱ�����8�ı���
           // Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");

           // ���ܳ׳�ʼ��Cipher����
           IvParameterSpec param = new IvParameterSpec(iv);
           cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

           // ��ʽִ�н��ܲ���
           byte decryptedData[] = cipher.doFinal(data);

           return decryptedData;
       } catch (Exception e) {
           System.err.println("DES�㷨�����ܳ�?");
           e.printStackTrace();
       }

       return null;
   }

}
