package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.UNBTFactory.*;
import static firok.unbt.tag.TagTestUtil.*;

public class TagLongTest
{
	private static void TagLongTest(String name,long value) throws IOException
	{
		File file = getFile(name);
		TagLong tag1 = new TagLong(name,value);
		write(file,tag1);
		TagLong tag2 = (TagLong) read(file);
		Assertions.assertEquals(tag1, tag2);
	}

	@Test
	public void TagLongTest() throws IOException
	{
		TagLongTest("long1",123);
		TagLongTest("long2",Long.MIN_VALUE);
		TagLongTest("long3",9876543210L);
		TagLongTest("long4",0);
		TagLongTest("long5",-123);
		TagLongTest("long6",-92938161432L);
		TagLongTest("long7",Long.MAX_VALUE);
	}
}
