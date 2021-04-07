package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagByteArrayTest
{
	private static void testEqual(String name, byte... value) throws IOException
	{
		File file = getFile(name);
		TagByteArray tag1 = new TagByteArray(name,value);
		write(file,tag1);
		TagByteArray tag2 = (TagByteArray) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		Random rand = new Random();
		testEqual("byte[]1",new byte[0]);
		testEqual("byte[]2",(byte)1,(byte)31);
		testEqual("byte[]3",new byte[]{1,2,3,4,5});
		testEqual("byte[]4",Byte.MIN_VALUE,Byte.MAX_VALUE);

		byte[] temp = new byte[50];
		rand.nextBytes(temp);
		testEqual("byte[]6",temp);
	}
}
