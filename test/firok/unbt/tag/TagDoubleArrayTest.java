package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagDoubleArrayTest
{
	private static void testEqual(String name, double... value) throws IOException
	{
		File file = getFile(name);
		TagDoubleArray tag1 = new TagDoubleArray(name,value);
		write(file,tag1);
		TagDoubleArray tag2 = (TagDoubleArray) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		Random rand = new Random();
		testEqual("double[]1",new double[0]);
		testEqual("double[]2",12337823213L,31946721121312L);
		testEqual("double[]3",1,2,3,4,5);
		testEqual("double[]4",Double.MIN_VALUE,Double.NaN,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
		testEqual("double[]5",Long.MAX_VALUE);

		double[] temp = new double[50];
		for(int i=0;i<temp.length;i++) temp[i] = rand.nextDouble() * Double.MAX_VALUE;
		testEqual("double[]6",temp);
	}
}
