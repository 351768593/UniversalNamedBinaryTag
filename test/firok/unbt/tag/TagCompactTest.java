package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static firok.unbt.tag.UNBTFactory.*;
import static firok.unbt.tag.TagTestUtil.*;

public class TagCompactTest
{
	private static void TagCompactTest(String name) throws IOException
	{
		File file = getFile(name);
		Random rand = new Random();
		TagCompact tag1 = new TagCompact(name);
		for(int i=0;i<50;i++)
		{
			TagBase tag2add;
			String tagName2add = "tag"+i;
			switch(rand.nextInt(4))
			{
				case 0:
					tag2add = new TagInt(tagName2add,rand.nextInt());
					break;
				case 1:
					tag2add = new TagLong(tagName2add,rand.nextLong());
					break;
				case 2:
					tag2add = new TagString(tagName2add,"value~~"+rand.nextLong());
					break;
				default: case 3:
				TagCompact tagNew = new TagCompact(tagName2add);
				tagNew.set(new TagInt("inner"+i+1,rand.nextInt()));
				tagNew.set(new TagLong("inner"+i+2,rand.nextLong()));
				tagNew.set(new TagString("inner"+i+3,"value~~"+rand.nextInt()));
				tag2add = tagNew;
				break;
			}

			tag1.set(tag2add);
		}
		write(file,tag1);

		TagCompact tag2 = (TagCompact) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void TagCompactTest() throws IOException
	{
		TagCompactTest("compact1");
		TagCompactTest("compact2");
		TagCompactTest("compact3");
		TagCompactTest("compact4");
		TagCompactTest("compact5");
	}
}
