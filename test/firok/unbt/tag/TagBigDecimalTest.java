package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagBigDecimalTest
{
	private static void testEqual(String name, BigDecimal value) throws IOException
	{
		File file = getFile(name);
		TagBigDecimal tag1 = new TagBigDecimal(name,value);
		write(file,tag1);
		TagBigDecimal tag2 = (TagBigDecimal) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("bd1",new BigDecimal(Float.MIN_VALUE));
		testEqual("bd2",new BigDecimal(Integer.MIN_VALUE));
//		testEqual("bd3",new BigDecimal(Float.NaN));
//		testEqual("bd4",new BigDecimal(Float.POSITIVE_INFINITY));
//		testEqual("bd5",new BigDecimal(Float.NEGATIVE_INFINITY));
//		testEqual("bd6",new BigDecimal(Double.POSITIVE_INFINITY));
//		testEqual("bd7",new BigDecimal(Double.NEGATIVE_INFINITY));
//		testEqual("bd8",new BigDecimal(Double.NaN));
		testEqual("bd9",new BigDecimal(Double.MAX_VALUE));
		testEqual("bd10",new BigDecimal(2313141));
		testEqual("bd11",new BigDecimal(Long.MAX_VALUE));
		testEqual("bd12",new BigDecimal(0));


	}
}
