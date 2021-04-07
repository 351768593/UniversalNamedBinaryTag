package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagFloatArrayTest
{
	private static void testEqual(String name, float... value) throws IOException
	{
		File file = getFile(name);
		TagFloatArray tag1 = new TagFloatArray(name,value);
		write(file,tag1);
		TagFloatArray tag2 = (TagFloatArray) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		Random rand = new Random();
		testEqual("float[]1",new float[0]);
		testEqual("float[]2",12337823213L,31946721121312L);
		testEqual("float[]3",1,2,3,4,5);
		testEqual("float[]4",Float.MIN_VALUE,Float.NaN,Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY);
		testEqual("float[]5",Long.MAX_VALUE);

		float[] temp = new float[50];
		for(int i=0;i<temp.length;i++) temp[i] = rand.nextFloat() * Float.MAX_VALUE;
		testEqual("float[]6",temp);
	}
}
