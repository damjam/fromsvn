package flink;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.dbcp.BasicDataSource;

public class SecretBasicDataSource extends BasicDataSource {

	
	public synchronized void setPassword(String password) {
		password = new String(Base64.decodeBase64(password));
		super.setPassword(password);
	}

	public static void main(String[] args) {
		System.out.println(MD5Util.MD5("cim1234"));
		//System.out.println(new String(Base64.encodeBase64("cim1234".getBytes())));
		
		//System.out.println(new String(Base64.decodeBase64("Y2ltMTIzNA==".getBytes())));
	}
}
