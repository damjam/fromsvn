package flink.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp 工具.
 * 
 * 
 * 
 */
public abstract class FtpUtils {
	/**
	 * 上传文件到server.
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static boolean upload(String ip, int port, String username, String password, 
			String path, String fileName, InputStream input) throws IOException {
		FTPClient ftp = new FTPClient();

		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.connect(addr, port);
			ftp.login(username, password);

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();
				
				return false;
			}
			
			if (StringUtils.isNotEmpty(path)) {
				ftp.changeWorkingDirectory(path);
			}
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.storeFile(fileName, input);
			
			return true;
		} 
		finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}

	public static boolean upload(String ip, int port, String username, String password, 
			String path, String fileName, String localPath, String localFileName) throws IOException {
		InputStream in = new FileInputStream(new File(localPath + File.separator + localFileName));
		boolean result = upload(ip, port, username, password, path, fileName, in);
		in.close();
		return result;
	}
	
	public static boolean download(String ip, int port, String username, String password, 
			String path, String fileName, String localPath, String localFileName) throws IOException {
		FTPClient ftp = new FTPClient();

		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.connect(addr, port);
			ftp.login(username, password);

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();
				
				return false;
			}
			
			if (StringUtils.isNotEmpty(path)) {
				ftp.changeWorkingDirectory(path);
			}
			
			InputStream in = ftp.retrieveFileStream(fileName);
			
			if (in == null) {
				throw new IOException("打开远程文件失败: " + fileName);
			}
			
			File localDir = new File(localPath);
			if (!localDir.exists()) {
				localDir.mkdirs();
			}
			
			File outFile = new File(localPath + File.separator + localFileName);
			outFile.delete();
			outFile.createNewFile();
			OutputStream os = new FileOutputStream(outFile);

			byte[] buffer = new byte[1024 * 100];
			while(true) {
				int count = in.read(buffer);
				if (count < 0) break;
				os.write(buffer, 0, count);
			}
			os.close();
			
			return true;
		} 
		finally {
			closeFtp(ftp);
		}
	}
	
	public static byte[] getFile(String ip, int port, String username, String password, String path, String fileName) {
		FTPClient ftp = new FTPClient();
		InputStream input = null;
		
		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.connect(addr, port);
			ftp.login(username, password);
			
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();
				
				return null;
			}
			
			if (StringUtils.isNotEmpty(path)) {
				ftp.changeWorkingDirectory(path);
			}
			
			return IOUtils.toByteArray(ftp.retrieveFileStream(fileName));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}
	
	public static long getFileSize(String ip, int port, String username, String password, String path, String fileName) {
		FTPClient ftp = new FTPClient();
		InputStream input = null;
		
		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.connect(addr, port);
			ftp.login(username, password);
			
			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();
				
				return 0;
			}
			
			if (StringUtils.isNotEmpty(path)) {
				ftp.changeWorkingDirectory(path);
			}
			
			return ftp.retrieveFileStream(fileName).available();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}

	/**
	 * 关闭.
	 * 
	 * @param input
	 * @param ftp
	 */
	private static void closeFtp(FTPClient ftp) {
		try {
			if (ftp != null) {
				ftp.logout();
				ftp.disconnect();
			}
		} catch (Exception e) {
			// do nothing.
		}
	}
	
	public static void main(String[] args) throws Exception {
		FtpUtils.download("10.211.16.129", 21, "root", "root", "/a/checkfile/M01", 
				"aaa", "d:/abc/def/aaa", "test.zip");
//		InputStream in = new FileInputStream(new File("d:/download/PowerDesiner.v12.0.zip"));
//		FtpUtils.upload("10.211.16.129", 21, "root", "root", "/ept/checkfile/M01", 
//				"aaa", in);
//		in.close();
	}
}
