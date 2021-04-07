package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.UNBTFactory.*;
import static firok.unbt.tag.TagTestUtil.*;

public class TagIntTest
{
	private static void TagIntTest(String name,int value) throws IOException
	{
		File file = getFile(name);
		TagInt tag1 = new TagInt(name,value);
		write(file,tag1);
		TagInt tag2 = (TagInt) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void TagIntTest() throws IOException
	{
		TagIntTest("int1",123);
		TagIntTest("int2",1234567890);
		TagIntTest("int3",987654321);
		TagIntTest("int4",0);
		TagIntTest("int5",-123);
		TagIntTest("int6",-362819312);
		TagIntTest("int7",-999999);
	}
}
