package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static firok.unbt.tag.TagTestUtil.getFile;
import static firok.unbt.tag.UNBTFactory.read;
import static firok.unbt.tag.UNBTFactory.write;

public class TagDateTest
{
	private static void testEqual(String name, Date value) throws IOException
	{
		File file = getFile(name);
		TagDate tag1 = new TagDate(name,value);
		write(file,tag1);
		TagDate tag2 = (TagDate) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void test() throws IOException
	{
		testEqual("date1",new Date());
		testEqual("date2",new Date(0));
		testEqual("date3",new Date(-213213141));
		testEqual("date4",new Date(231940216390217412L));
		testEqual("date5",new java.sql.Date(2194123));
		testEqual("date6",new java.sql.Date(1231251513413L));
		testEqual("date7",new java.sql.Date(0));
	}
}
