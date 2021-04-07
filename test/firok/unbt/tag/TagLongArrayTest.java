package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagLongArrayTest
{
	private static void testEqual(String name, long... value) throws IOException
	{
		File file = getFile(name);
		TagLongArray tag1 = new TagLongArray(name,value);
		write(file,tag1);
		TagLongArray tag2 = (TagLongArray) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("long[]1",new long[0]);
		testEqual("long[]2",12337823213L,31946721121312L);
		testEqual("long[]3",1,2,3,4,5);
		testEqual("long[]4",Long.MIN_VALUE);
		testEqual("long[]5",Long.MAX_VALUE);
		testEqual("long[]6");
		long[] temp = new long[50];
		for(int i=0;i<temp.length;i++) temp[i] = (long)i*i*i;
		testEqual("long[]7 ",temp);
	}
}
