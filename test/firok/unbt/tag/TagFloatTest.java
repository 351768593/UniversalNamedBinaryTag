package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.UNBTFactory.*;
import static firok.unbt.tag.TagTestUtil.*;

public class TagFloatTest
{
	private static void testEqual(String name, float value) throws IOException
	{
		File file = getFile(name);
		TagFloat tag1 = new TagFloat(name,value);
		write(file,tag1);
		TagFloat tag2 = (TagFloat) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("float1",Float.MIN_VALUE);
		testEqual("float2",Float.MIN_NORMAL);
		testEqual("float3",Float.MAX_VALUE);
		testEqual("float4",Float.NaN);
		testEqual("float5",0);
		testEqual("float6",123.456f);
		testEqual("float7",-1234567.3214353f);
		testEqual("float8",Float.NEGATIVE_INFINITY);
		testEqual("float9",Float.POSITIVE_INFINITY);
	}
}
