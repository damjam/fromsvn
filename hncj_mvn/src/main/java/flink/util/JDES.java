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
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = m_desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
	    //Cipher cipher = Cipher.getInstance("DES");
	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		FileInputStream fin=new FileInputStream(inFile); 
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
		fout.write(decryptText); 
		fout.close(); 
		fin.close(); 
			
		
	}
	public void doECBDecrypt_File(String inFile,String outFile) throws Exception
	{
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = m_desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       
        cipher.init(Cipher.DECRYPT_MODE, key, sr);

	       
		FileInputStream fin=new FileInputStream(inFile); 
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
		fout.write(decryptText); 
		fout.close(); 
		fin.close(); 
			
	}
   
   public static byte[] generateKey() {
       try {

           SecureRandom sr = new SecureRandom();

           KeyGenerator kg = KeyGenerator.getInstance("DES");
           kg.init(sr);

           SecretKey secretKey = kg.generateKey();

           byte[] key = secretKey.getEncoded();

           return key;
       } catch (NoSuchAlgorithmException e) {
           System.err.println("DES�㷨�������Կ���!");
           e.printStackTrace();
       }

       return null;
   }
   public byte[] doECBEncrypt(byte[] plainText,int len) throws Exception 
   {
       SecureRandom sr = new SecureRandom();
       byte rawKeyData[] = m_desKey;
       DESKeySpec dks = new DESKeySpec(rawKeyData);
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
       SecretKey key = keyFactory.generateSecret(dks);
       Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       cipher.init(Cipher.ENCRYPT_MODE, key, sr);
       byte data[] = plainText;
       byte encryptedData[] = cipher.doFinal(data,0,len);
       
       return encryptedData;
   }
   public byte[] doECBDecrypt(byte[] encryptText,int len) throws Exception 
   {
       SecureRandom sr = new SecureRandom();
       byte rawKeyData[] = m_desKey;
       DESKeySpec dks = new DESKeySpec(rawKeyData);
       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
       SecretKey key = keyFactory.generateSecret(dks);
       Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
       
       cipher.init(Cipher.DECRYPT_MODE, key, sr);
       byte encryptedData[] = encryptText;/* ��þ�����ܵ���� */
       byte decryptedData[] = cipher.doFinal(encryptedData,0,len);
       return decryptedData;
   }

   
   public byte[] CBCEncrypt(byte[] data, byte[] iv) {

       try {
           DESKeySpec dks = new DESKeySpec(m_desKey);

           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           SecretKey secretKey = keyFactory.generateSecret(dks);

           Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
           IvParameterSpec param = new IvParameterSpec(iv);
           cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);

           byte encryptedData[] = cipher.doFinal(data);

           return encryptedData;
       } catch (Exception e) {
           e.printStackTrace();
       }

       return null;
   }

   public byte[] CBCDecrypt(byte[] data,byte[] iv) {
       try {
           DESKeySpec dks = new DESKeySpec(m_desKey);

           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           SecretKey secretKey = keyFactory.generateSecret(dks);

           // using DES in CBC mode
           Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
           IvParameterSpec param = new IvParameterSpec(iv);
           cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

           byte decryptedData[] = cipher.doFinal(data);

           return decryptedData;
       } catch (Exception e) {
           e.printStackTrace();
       }

       return null;
   }

}
