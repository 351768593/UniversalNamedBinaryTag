package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static firok.unbt.tag.UNBTFactory.*;
import static firok.unbt.tag.TagTestUtil.*;

public class TagStringTest
{
	private static void TagStringTest(String name,String value) throws IOException
	{
		File file = getFile(name);
		TagString tag1 = new TagString(name,value);
		write(file,tag1);
		TagString tag2 = (TagString) read(file);
		Assertions.assertEquals(tag1, tag2);
	}
	@Test
	public void TagStringTest() throws IOException
	{
		TagStringTest("str1","");
		TagStringTest("str2","123 abc");
		TagStringTest("str3","你我他 123");
		TagStringTest("str4","2168930729143712945628137219-3472198456123839213-721954612y343890217319asbdjsabdowadiaw2-我我我");
		TagStringTest("str5","ejwoandkwadwapdnkfbnc\uD83D\uDC9A💢☪🕉🕉💥☯💨✝🥠🥠🧈🍚🦪🍣🥨");
	}
}
