package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagDoubleTest
{
	private static void testEqual(String name, double value) throws IOException
	{
		File file = getFile(name);
		TagDouble tag1 = new TagDouble(name,value);
		write(file,tag1);
		TagDouble tag2 = (TagDouble) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("double1",Double.MIN_VALUE);
		testEqual("double2",Double.MIN_NORMAL);
		testEqual("double3",Double.MAX_VALUE);
		testEqual("double4",Double.NaN);
		testEqual("double5",0);
		testEqual("double6",123.456f);
		testEqual("double7",-1234567.3214353f);
		testEqual("double8",Double.NEGATIVE_INFINITY);
		testEqual("double9",Double.POSITIVE_INFINITY);

		testEqual("double10",Float.NaN);
		testEqual("double11",Float.POSITIVE_INFINITY);
		testEqual("double12",Float.NEGATIVE_INFINITY);
	}
}
