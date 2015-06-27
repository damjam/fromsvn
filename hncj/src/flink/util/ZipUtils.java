package flink.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

/**
 * zip 压缩工具, 仅实现ZipStream.
 * 
 * 
 */
public abstract class ZipUtils {
	/**
	 * zip 解压字节数组.
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unzip(byte[] data) {
		if (data == null) {
			return null;
		}

		ByteArrayOutputStream bos = null;
		ByteArrayInputStream bis = null;
		ZipInputStream zip = null;

		try {
			bos = new ByteArrayOutputStream();
			bis = new ByteArrayInputStream(data);
			zip = new ZipInputStream(bis);

			while (zip.getNextEntry() != null) {
				byte[] b = IOUtils.toByteArray(zip);
				IOUtils.write(b, bos);
				bos.flush();
			}

			return bos.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(zip);
		}

		return null;
	}

	/**
	 * zip 压缩字节数组.
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] zip(byte[] data) {
		if (data == null) {
			return null;
		}

		ByteArrayOutputStream bos = null;
		ZipOutputStream zip = null;

		try {
			bos = new ByteArrayOutputStream();
			zip = new ZipOutputStream(bos);

			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();

			return bos.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(zip);
		}

		return null;
	}
}
