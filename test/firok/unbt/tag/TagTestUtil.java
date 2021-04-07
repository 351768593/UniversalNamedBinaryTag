package firok.unbt.tag;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class TagTestUtil
{
	public static final File dir = new File("V://dev_cache/plain");
	static
	{
		dir.mkdirs();
	}
	public static File getFile(String name)
	{
		return new File(dir,name+".unbt");
	}

	private static void writeZIP(File file,TagBase tag) throws IOException
	{
		try(
				OutputStream os = new FileOutputStream(file);
				ZipOutputStream zos = new ZipOutputStream(os);
				DataOutputStream dos = new DataOutputStream(zos);
		) {
			zos.putNextEntry(new ZipEntry(file.getName()));
			UNBTFactory.write(dos,new UNBTWritingContext(),tag);
			dos.flush();
		}
	}

	public static TagBase readZIP(File file) throws IOException
	{
		try(
				InputStream is = new FileInputStream(file);
				ZipInputStream zis = new ZipInputStream(is);
				DataInputStream dis = new DataInputStream(zis);
		){
			return UNBTFactory.read(dis);
		}
	}
}
