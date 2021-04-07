package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagIntArrayTest
{
	private static void testEqual(String name, int... value) throws IOException
	{
		File file = getFile(name);
		TagIntArray tag1 = new TagIntArray(name,value);
		write(file,tag1);
		TagIntArray tag2 = (TagIntArray) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("int[]1",new int[0]);
		testEqual("int[]2",123);
		testEqual("int[]3",1,2,3,4,5);
		testEqual("int[]4",Integer.MIN_VALUE);
		testEqual("int[]5",Integer.MAX_VALUE);
		testEqual("int[]6");
		int[] temp = new int[50];
		for(int i=0;i<temp.length;i++) temp[i] = i;
		testEqual("int[]7 ",temp);
	}
}
