package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagBigIntegerTest
{
	private static void testEqual(String name, BigInteger value) throws IOException
	{
		File file = getFile(name);
		TagBigInteger tag1 = new TagBigInteger(name,value);
		write(file,tag1);
		TagBigInteger tag2 = (TagBigInteger) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("bi1",BigInteger.valueOf(Long.MAX_VALUE));
		testEqual("bi2",BigInteger.ZERO);
		testEqual("bi3",new BigInteger("123168923462183712904217933281904561290371289037219031"));
		testEqual("bi4",new BigInteger("278931002"));
	}
}
